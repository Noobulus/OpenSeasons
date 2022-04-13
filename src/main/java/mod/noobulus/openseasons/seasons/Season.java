package mod.noobulus.openseasons.seasons;

public class Season {
    private int days;
    private float tempMod;
    private float humidMod;
    private String name;

    public Season() {
        this.days = 28;
        this.tempMod = 0f;
        this.humidMod = 0f;
        this.name = "Default";
    }

    public Season(int days, float tempMod, float humidMod, String name) {
        this.days = days;
        this.tempMod = tempMod;
        this.humidMod = humidMod;
        this.name = name;
    }

    public int getDays() {
        return this.days;
    }

    public float getTempMod() {
        return this.tempMod;
    }

    public float getHumidMod() {
        return this.humidMod;
    }

    public String getName() {
        return this.name;
    }
}
