from cloudapp import app

@app.route("/")
def index():
    return "Hello flask devs!"