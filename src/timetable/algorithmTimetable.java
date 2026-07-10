package timetable;
/**
 * Данный файл содержит алгоритмы для работы с расписанием сотрудников, он принимает значения List из readingDataBase
 * и возвразает их для GUI.
 * Реализованы следующие методы:
 * filerByDistrict() -- фильтрование сотрудников по району клиента
 * sortByRating() -- сотрировка по убыванию рейтинга
 * sortByAvailability() -- сортирует по количеству свободных мест для принятия клиентов
 * isAvailable() -- проверяет, может ли сотрудник взять еще одного клиента
 * findBestCandidates() -- метод для фильтрации и сортировки за один вызов, для клиента из заданного района
 * getBestEmployy() -- возвращает одного лучшего сотрудника
  */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class algorithmTimetable {
    public List<Employee> filterByDistrict(List<Employee> employees, String clientDistrict){
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees){
            if(emp.getDistrict().equalsIgnoreCase(clientDistrict)){
                result.add(emp);
            }
        }
        return result;
    }

    public List<Employee> sortByRating(List<Employee> employees){
        List<Employee> sorted = new ArrayList<>(employees);
        sorted.sort(Comparator.comparingInt(Employee::getRating).reversed());
        return sorted;
    }

    public List<Employee> sortByAvailability(List<Employee> employees){
        List<Employee> sorted = new ArrayList<>(employees);
        sorted.sort(Comparator.comparingInt(Employee::getMaxclients).reversed());
        return sorted;
    }

    public boolean isAvailability(Employee employee, int currentClients){
        return currentClients < employee.getMaxclients();
    }

    private double computeScore(Employee emp){
        return emp.getRating() * 2.0 + emp.getMaxclients();
    }

    public List<Employee> findBestCandidates(List<Employee> employees, String clientDistrict){
        List<Employee> filtered = filterByDistrict(employees, clientDistrict);
        if (filtered.isEmpty()){
            return filtered;
        }
        filtered.sort(Comparator.comparingDouble(this::computeScore).reversed());
        return filtered;
    }

    public Employee getBestEmployee(List<Employee> employees, String clientDistrict){
        List<Employee> candidates = findBestCandidates(employees, clientDistrict);
        if (candidates.isEmpty()){
            return null;
        }
        return candidates.get(0);
    }

    public List<String> getAllDistricts(List<Employee> employees){
        List<String> districts = new ArrayList<>();
        for (Employee emp : employees){
            if (!districts.contains(emp.getDistrict())){
                districts.add(emp.getDistrict());
            }
        }
        districts.sort(String::compareTo);
        return districts;
    }
}
