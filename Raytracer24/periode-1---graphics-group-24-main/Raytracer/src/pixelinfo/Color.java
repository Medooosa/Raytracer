package pixelinfo;

public class Color {
    private float red;
    private float green;
    private float blue;

    public Color(float lerp, float lerp2, float lerp3) {
        this.red = lerp;
        this.green = lerp2;
        this.blue = lerp3;
    }

    public float getR(){
        return red;
    }

    public float getG(){
        return green;
    }

    public float getB(){
        return blue;
    }

    public Color getColor(){
        return new Color(red, green, blue);
    }

    public Color multiplyColor(float intensity){
        float tempRed = red * intensity;
        float tempGreen = green * intensity;
        float tempBlue = blue * intensity;
        return new Color(tempRed, tempGreen, tempBlue);
    }

    private static float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    public static Color lerp(Color a, Color b, float t) {
        return new Color(lerp(a.getR(), b.getR(), t), lerp(a.getG(), b.getG(), t), lerp(a.getB(), b.getB(), t));
    }

    public static Color fromInt(int argb) {
        int b = (argb)&0xFF;
        int g = (argb>>8)&0xFF;
        int r = (argb>>16)&0xFF;

        return new Color(r/255F, g/255F, b/255F);
    }

    public static final Color GRAY = new Color(1.0F, 0.0F, 1.0F);
    public static final Color DARK_GRAY = new Color(0.2F, 0.2F, 0.2F);
}