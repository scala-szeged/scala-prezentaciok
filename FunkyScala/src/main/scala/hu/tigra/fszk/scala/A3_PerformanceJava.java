package hu.tigra.fszk.scala;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 2017.03.29.
 * Time: 16:24
 */
public class A3_PerformanceJava {

    private static class Person {

        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    public static List<Person> createPeople() {
        return Arrays.asList(new Person("Tomika", 12),
                new Person("Petike", 14),
                new Person("Jancsi", 21),
                new Person("Julcsi", 18),
                new Person("Paula", 20),
                new Person("Paulina", 31),
                new Person("McAffee", 30),
                new Person("McDonalds", 69),
                new Person("McMester", 16));
    }

    public static void main(String[] args) {

        List<Person> people = createPeople();

        long sfunc = System.currentTimeMillis();

        people.stream()
                .filter(person -> person.getAge() > 17)
                .map(Person::getName)
                .map(String::toUpperCase)
                .findFirst()
                .ifPresent(System.out::println);

        long efunc = System.currentTimeMillis();
        System.out.println(efunc - sfunc);
        System.out.println();


        long simp = System.currentTimeMillis();
        Person firstAdult = null;
        for (Person person : people) {
            if (person.getAge() > 17) {
                firstAdult = person;
                break;
            }
        }
        if (firstAdult != null) {
            System.out.println("The first adult in the list is "
                    + firstAdult.getName());
        } else {
            System.out.println("No adults in the given list");
        }
        long eimp = System.currentTimeMillis();
        System.out.println(eimp - simp);
    }
}
