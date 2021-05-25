package sample;

public class Bar implements Comparable<Bar> {
    //Feel free to add other necessary variables
// Creates a new bar.
    String name;
    String category;
    int value;
    String country;
    String year;

    public Bar(String name, String category, int value, String country, String year) {
        this.name = name;
        this.category = category;
        this.value = value;
        this.country = country;
        this.year = year;
    }
    // Returns the name of this bar.
    public String getName() {
        return  name;
    }
    // Returns the category of this bar.
    public String getCategory() {
        return category;
    }
    // Returns the value of this bar.
    public int getValue() {
        return value;
    }
    public String getCountry() {
        return country;
    }

    public String getYear() {
        return year;
    }
    // Compares two bars by value.
    public int compareTo(Bar other) {
        return Integer.compare(this.value, other.value);
    }
//Feel free to add other necessary method
}
