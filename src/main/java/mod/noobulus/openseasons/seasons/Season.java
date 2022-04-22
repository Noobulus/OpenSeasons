package mod.noobulus.openseasons.seasons;

public class Season {
    private int days;
    private float tempMod;
    private float humidMod;
    private String name;
    private float[] weatherMods;

    public Season() {
        this.days = 28;
        this.tempMod = 0f;
        this.humidMod = 0f;
        this.name = "Default";
        this.weatherMods = new float[]{1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F};
    }

    public Season(int days, float tempMod, float humidMod, String name, float[] weatherMods) {
        this.days = days;
        this.tempMod = tempMod;
        this.humidMod = humidMod;
        this.name = name;
        this.weatherMods = weatherMods;
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

    public float getMinRainTimeMod() {
        return this.weatherMods[0];
    }

    public float getMaxRainTimeMod() {
        return this.weatherMods[1];
    }

    public float getMinRainDelayMod() {
        return this.weatherMods[2];
    }

    public float getMaxRainDelayMod() {
        return this.weatherMods[3];
    }

    public float getMinThunderTimeMod() {
        return this.weatherMods[4];
    }

    public float getMaxThunderTimeMod() {
        return this.weatherMods[5];
    }

    public float getMinThunderDelayMod() {
        return this.weatherMods[6];
    }

    public float getMaxThunderDelayMod() {
        return this.weatherMods[7];
    }
}
