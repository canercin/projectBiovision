import keras
import tensorflow as tf
import numpy as np
import cv2
import os
from keras.api.models import load_model
from keras.api.preprocessing import image


def choose_and_load_model(model_type):
    """
    Choose a model based on the chosen type.
    :param model_type: indicates the type of model to be used.
    :return: selected model
    """
    match model_type:
        case "0":
            print("Skin Cancer U-Net model selected")
            model = load_model("models/unet_model.keras")
            return model
        case "1":
            print("Skin Cancer CNN model selected")
            model = load_model("models/skin_model.h5")
            return model
        case "2":
            print("Breast Cancer CNN model selected")
            model = load_model("models/breast_model.h5", compile=False)
            return model
        case "3":
            print("Brain Cancer CNN model selected")
            model = load_model("models/brain_model.h5")
            return model
        case "4":
            print("Blood Cancer CNN model selected")
            model = load_model("models/blood_model.h5")
            return model
        case _:
            raise ValueError("Invalid model type selected.")


def preprocess_image_for_cnn(image_path: str):
    """
    Preprocess the image for model input.
    :param image_path: path to the image file.
    :return: preprocessed image.
    """
    full_image_path = os.path.join(f"/home/{os.getenv('USER')}/biovision/original/", image_path)
    img_resized = image.load_img(full_image_path, target_size=(180, 180))
    img_array = image.img_to_array(img_resized)
    img_array_expanded = np.expand_dims(img_array, axis=0)
    final_image = img_array_expanded / 255.0
    return final_image


def predict_image_for_cnn(model_type, image_path):
    """
    Predict the image using CNN model.
    :param model_type: Model type of CNN.
    :param image_path: path to the image file.
    :return: predicted image.
    """
    model = choose_and_load_model(model_type)
    preprocessed_image = preprocess_image_for_cnn(image_path)
    prediction = model.predict(preprocessed_image)
    print(prediction)
    predicted_class_index = prediction[0][0] > 0.5
    if model_type != "1" or not predicted_class_index :
        return None, predicted_class_index
    else:
        unet_image = predict_image_for_unet(image_path)
        name = rename_original_to_masked(image_path)
        output_dir = f"/home/{os.getenv('USER')}/biovision/masked_images/"
        os.makedirs(output_dir, exist_ok=True)
        output_path = os.path.join(output_dir, os.path.basename(name))
        cv2.imwrite(output_path, unet_image * 255, [cv2.IMWRITE_JPEG_QUALITY, 95])

        # G-code dosyasını oluştur
        gcode_output_dir = f"/home/{os.getenv('USER')}/biovision/gcodes/"
        os.makedirs(gcode_output_dir, exist_ok=True)
        gcode_output_path = os.path.join(gcode_output_dir, os.path.splitext(os.path.basename(name))[0] + ".gcode")
        generate_gcode_from_image(output_path, gcode_output_path)

        return name, predicted_class_index, gcode_output_path


def preprocess_image_for_unet(image_path: str):
    """
    Preprocess the image for UNet model input.
    :param image_path: path to the image file.
    :return: preprocessed image.
    """
    img_resized = image.load_img(image_path, target_size=(180, 180))
    img_array = image.img_to_array(img_resized)
    img_normalized = img_array / 255.0
    final_image = np.expand_dims(img_normalized, axis=0)
    return final_image


def predict_image_for_unet(image_path):
    """
    Predict the image using UNet model.
    :param image_path: path to the image file.
    :return: predicted image.
    """
    model = choose_and_load_model("0")
    preprocessed_image = preprocess_image_for_unet(image_path)
    prediction = model.predict(preprocessed_image)
    predicted_image = (prediction[0] > 0.5).astype(np.uint8)
    result_image = morf(predicted_image)
    return result_image


def morf(image_to_morph):
    kernel = np.ones((5, 5), np.uint8)
    dilated = cv2.dilate(image_to_morph, kernel, iterations=1)
    closed = cv2.erode(dilated, kernel, iterations=1)
    inverted = cv2.bitwise_not(closed)
    inverted = cv2.morphologyEx(inverted, cv2.MORPH_CLOSE, kernel)
    result = cv2.bitwise_not(inverted)
    return result


def rename_original_to_masked(image_path: str):
    """
    Rename the original image to masked image.
    :param image_path: path to the image file.
    :return: renamed image path.
    """
    new_name = image_path.replace("original", "masked")
    return new_name


def generate_gcode_from_image(image_path, output_path, scale=1.0):
    # Resmi yükle ve gri tonlamaya çevir
    image_to_gcode = cv2.imread(image_path, cv2.IMREAD_GRAYSCALE)
    height, width = image_to_gcode.shape
    _, binary_image = cv2.threshold(image_to_gcode, 127, 255, cv2.THRESH_BINARY)

    # Konturları bul
    contours, _ = cv2.findContours(binary_image, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    # G-kod dosyasını oluştur ve başlangıç komutlarını yaz
    with open(output_path, "w") as gcode_file:
        gcode_file.write("G21 ; Set units to millimeters\n")
        gcode_file.write("G90 ; Absolute positioning\n")
        gcode_file.write("G28 ; Home all axes\n")
        gcode_file.write("G1 Z5.0 F500 ; Lift pen\n")

        for contour in contours:
            # İlk kontur noktasına git ve kalemi indir
            x, y = contour[0][0]
            x *= scale
            y = (height - y) * scale  # Y eksenini ters çeviriyoruz
            gcode_file.write(f"G1 X{x:.2f} Y{y:.2f} F1500 ; Move to start of contour\n")
            gcode_file.write("G1 Z0.0 F500 ; Lower pen\n")

            # Kontur noktalarını takip ederek çizim yap
            for point in contour:
                x, y = point[0]
                x *= scale
                y = (height - y) * scale  # Y eksenini ters çeviriyoruz
                gcode_file.write(f"G1 X{x:.2f} Y{y:.2f} F1500\n")

            # Kontur bittiğinde kalemi kaldır
            gcode_file.write("G1 Z5.0 F500 ; Lift pen\n")

        # İşlem bitti, kalemi yukarıda tut ve bitir
        gcode_file.write("G1 Z5.0 F500 ; Lift pen\n")
        gcode_file.write("M84 ; Disable motors\n")
