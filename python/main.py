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
    model_type = data.get('model_type')
    image_path = data.get('image_path')

    if model_type is None or image_path is None:
        return jsonify({'error': 'model_type and image_path are required'}), 400

    unet_result, is_cancer = model_operations.predict_image_for_cnn(model_type, image_path)

    return jsonify({'unet_result': unet_result, 'is_cancer': bool(is_cancer)})


if __name__ == '__main__':
    # Run the Flask app
    app.run(debug=True)

