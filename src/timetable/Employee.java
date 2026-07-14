package timetable;

/**
 * Данный файл нужен для содержания данных одной строки из базы данных в виде объекта Java
 */
public class Employee {
    private int id;
    private String lastname;
    private  String firstname;
    private  String midlename;
    private int rating;
    private String district;
    private int maxclients;

    public Employee(int id, String lastname, String firstname, String midlename, int rating,
                    String district, int maxclients){
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.midlename = midlename;
        this.rating = rating;
        this.district = district;
        this.maxclients = maxclients;
    }

    public int getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMidlename() {
        return midlename;
    }

    public int getRating() {
        return rating;
    }

    public String getDistrict() { return district; }

    public int getMaxclients() {
        return maxclients;
    }

    public static String getEmployeeFullName(Employee emp){
        return emp.lastname + " " + emp.firstname + " " + emp.midlename;
    }

    public String toString(){
        return lastname + " " + firstname + " " + midlename +  " (ID " + id + " ) ";
    }
}
