from flask import Flask, jsonify, request
import model_operations

app = Flask(__name__)

@app.route('/choose_and_run_model', methods=['POST'])
def choose_and_run_model():
    """
    Flask endpoint to choose a model and run it on the provided image.
    Expects JSON payload with 'model_type' and 'image_path'.
    """
    data = request.get_json()
    model_type = str(data.get('model_type'))
    image_path = data.get('image_path')

    if model_type is None or image_path is None:
        return jsonify({'error': 'model_type and image_path are required'}), 400

    result = model_operations.predict_image_for_cnn(model_type, image_path)

    # predict_image_for_cnn artık üç değer döndürüyor
    if isinstance(result, tuple) and len(result) == 3:
        unet_result, is_cancer, gcode_path = result
    else:
        unet_result, is_cancer = result
        gcode_path = None

    print(is_cancer)

    return jsonify({
        'unet_result': unet_result,
        'is_cancer': bool(is_cancer),
        'gcode_path': gcode_path
    })


if __name__ == '__main__':
    # Run the Flask app
    app.run(debug=True)
