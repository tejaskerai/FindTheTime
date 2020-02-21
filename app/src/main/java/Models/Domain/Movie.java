package Models.Domain;

public class Movie {

    int filmId;
    String filmName;
    String releaseDate;
    String ageRating;
    String trailer;
    String plot;

    public Movie(int filmId, String filmName, String releaseDate, String ageRating, String trailer, String plot) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.releaseDate = releaseDate;
        this.ageRating = ageRating;
        this.trailer = trailer;
        this.plot = plot;
    }


    public int getFilmId() {
        return filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getPlot() {
        return plot;
    }
}
