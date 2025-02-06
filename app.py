from flask import Flask, jsonify

app = Flask(__name__)

@app.route('/status/info', methods=['GET'])
def status():
    return jsonify({"health": "All systems operational"}), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0')
