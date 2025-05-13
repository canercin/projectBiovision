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
        case 0:
            print("Skin Cancer U-Net model selected")
            model = load_model("models/unet_model.keras")
            return model
        case "1":
            print("Skin Cancer CNN model selected")
            model = load_model("models/skin_model.h5")
            return model
        case 2:
            print("Breast Cancer CNN model selected")
            model = load_model("models/breast_model.h5")
            return model
        case 3:
            print("Brain Cancer CNN model selected")
            model = load_model("models/brain_model.h5")
            return model
        case 4:
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
    img_resized = image.load_img(image_path, target_size=(180, 180))
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
    predicted_class_index = prediction[0][0] > 0.5
    if model_type != 1:
        return None, predicted_class_index
    else:
        unet_image = predict_image_for_unet(image_path)
        name = rename_original_to_masked(image_path)
        output_dir = f"/home/{os.getenv('USER')}/masked_images/"
        os.makedirs(output_dir, exist_ok=True)
        output_path = os.path.join(output_dir, os.path.basename(name))
        cv2.imwrite(output_path, unet_image * 255, [cv2.IMWRITE_JPEG_QUALITY, 95])
        return name, predicted_class_index


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
    model = choose_and_load_model(0)
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

