from .methods import build_model

class ModelWrapper:
    def __init__(self):
        self.model = build_model()  # TensorFlow model
        self.qs = None  # Placeholder for Q-values