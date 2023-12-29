package me.jong1;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class FieldExample {
    public static void main( String[] args ) throws ClassNotFoundException {
        //Class를 가져오는 방법들
        Class<Book> bookClass = Book.class;

        Book book  = new Book();
        Class<? extends Book> aClass = book.getClass();

        Class<?> aClass1 = Class.forName("me.jong1.Book");

        //이건 Public밖에 Return하지 않는다.
        Arrays.stream(bookClass.getFields()).forEach(System.out::println);

        System.out.println("================================================================");

        // Private까지 모두 Return받는 방법
        Arrays.stream(bookClass.getDeclaredFields()).forEach(System.out::println);

        System.out.println("================================================================");

        // Field와 Field에 포함되어 있는 값을 가져오는 방법
        Arrays.stream(bookClass.getDeclaredFields()).forEach(f -> {
            try {
                //private에 바로 직접적인 접근은 불가능하다.
                System.out.printf("%s, %s\n", f, f.get(book));
            } catch (IllegalAccessException e) {
                System.out.println("Private을 가져오려 하면 오류나지롱~");
            }
        });


        System.out.println("================================================================");
        Arrays.stream(bookClass.getDeclaredFields()).forEach(f -> {
            try {
                f.setAccessible(true);
                System.out.printf("%s, %s\n", f, f.get(book));
            } catch (IllegalAccessException e) {
                System.out.println("여긴 왜 오류야?");
            }
        });
    }
}
