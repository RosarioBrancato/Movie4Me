package ch.fhnw.movie4me.dto;

import com.google.gson.annotations.SerializedName;

public class Cast {

    private final String IMG_URL_ROOT = "https://image.tmdb.org/t/p/w500";

    @SerializedName("cast_id")
    private int castId;

    private String character;

    @SerializedName("credit_id")
    private String creditId;

    private int gender;
    private int id;
    private String name;
    private int order;

    @SerializedName("profile_path")
    private String profilePath;


    public String getPosterUrl() {
        String url = null;

        String posterPath = this.getProfilePath();
        if (posterPath != null) {
            url = IMG_URL_ROOT + posterPath;
        }

        return url;
    }


    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
