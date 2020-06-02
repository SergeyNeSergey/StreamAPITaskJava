/*Напишите программу, читающую из System.in текст в кодировке UTF-8, подсчитывающую в нем частоту появления слов,
 и в конце выводящую 10 наиболее часто встречающихся слов. Словом будем считать любую непрерывную последовательность
 символов, состоящую только из букв и цифр. Например, в строке "Мама мыла раму 33 раза!" ровно пять слов: "Мама",
 "мыла", "раму", "33" и "раза". Подсчет слов должен выполняться без учета регистра, т.е. "МАМА", "мама" и
 "Мама" — это одно и то же слово. Выводите слова в нижнем регистре.
 Если в тексте меньше 10 уникальных слов, то выводите сколько есть.
 Если в тексте некоторые слова имеют одинаковую частоту, т.е. их нельзя однозначно упорядочить только по частоте,
 то дополнительно упорядочите слова с одинаковой частотой в лексикографическом порядке.

Задача имеет красивое решение через стримы без циклов и условных операторов. Попробуйте придумать его.*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestTaskStreamAPI {

    public static void main(String[] args) {
        // Используя трай с ресурсами получаю поток из консоли и присваиваю его объекту класса БуффередРидер.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            // считываю текст, привожу его к нижнему регистру, все знаки пунктуации меняю на пробел, получаю массив строк
            //разделённых пробелом.
            reader.lines().map(line -> line.toLowerCase().replaceAll("\\p{Punct}", " ").split("\\s+"))
                     //Получаю Стрим АПИ из массива строк
                    .flatMap(Arrays::stream)
                    //собираю объект класса МАП. Ключ в котором слово, а значение-частота употребления.
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    // конвертирую объект класса мап в стрим АПИ.
                    .entrySet().stream().
                    // сортирую его по ключу
                            sorted(Map.Entry.comparingByKey())
                    //сортирую его по значению
                    .sorted(Comparator.comparing((Function<Map.Entry<String, Long>, Long>) Map.Entry::getValue)
                            // переворачиваю
                            .reversed())
                    // получаю ключ (слово) и лимитирую первыми десятью позициями.
                    .map(n -> n.getKey()).limit(10)
                    //вывожу в консоль
                    .forEach(System.out::println);
        }
        // Ловлю все исключения вывожу на экран ошибку в случае их наличия
        catch (IOException e) {
            System.out.println("error");
        }


    }
}
