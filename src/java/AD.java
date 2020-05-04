public class AD {
    public double _value;
    public double _deriv;

    public static int EXPONENT = 4;
    
    public AD(double value, double deriv) {
        _value = value;
        _deriv = deriv;
    }

    public AD mul(AD x) {
        return new AD(_value*x._value, _value*x._deriv + _deriv*x._value);
    }

    public AD raiseToPower() {
        AD result = new AD(1.0, 0.0);
        for (int i = 0; i < EXPONENT; i++) {
            result = result.mul(this);
        }
        return result;
    }
}
