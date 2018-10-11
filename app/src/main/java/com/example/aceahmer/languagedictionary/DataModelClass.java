package com.example.aceahmer.languagedictionary;

public class DataModelClass {

    private int imageId;
    private String englishVersion;
    private String urduVersion;
    private int audioFile;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getEnglishVersion() {
        return englishVersion;
    }

    public void setEnglishVersion(String englishVersion) {
        this.englishVersion = englishVersion;
    }

    public String getUrduVersion() {
        return urduVersion;
    }

    public void setUrduVersion(String urduVersion) {
        this.urduVersion = urduVersion;
    }

    public int getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(int audioFile) {
        this.audioFile = audioFile;
    }

    public DataModelClass(int imageId, String englishVersion, String urduVersion, int audioFile) {
        this.imageId = imageId;
        this.englishVersion = englishVersion;
        this.urduVersion = urduVersion;
        this.audioFile = audioFile;
    }
}
