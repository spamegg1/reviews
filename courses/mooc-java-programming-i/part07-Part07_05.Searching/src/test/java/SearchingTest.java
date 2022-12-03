
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.junit.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SearchingTest {

    @Test
    @Points("07-05.1")
    public void linearSearchCanFindABook() throws Throwable {
        ArrayList<Book> books = generateBooks(5);
        for (int i = 0; i < books.size(); i++) {
            int index = Searching.linearSearch(books, books.get(i).getId());

            assertTrue("Linear search didn't find the book searched for, even though the book was on the list. Try the search for the index " + i + " with the following books:\n" + books.toString(), i == index);
        }
    }

    @Test
    @Points("07-05.1")
    public void linearSearchCanFindABookBig() throws Throwable {
        ArrayList<Book> books = generateBooks(100);
        for (int i = 0; i < books.size(); i++) {
            int index = Searching.linearSearch(books, books.get(i).getId());

            assertTrue("Linear search didn't find the book searched for, even though the book was on the list. Try the search with a large number of books.", i == index);
        }
    }

    @Test
    @Points("07-05.1")
    public void aNonExistingBookIsNotFound() throws Throwable {
        ArrayList<Book> books = generateBooks(100);
        Set<Integer> idt = books.stream().map(k -> k.getId()).collect(Collectors.toSet());

        for (int i = 0; i < 100; i++) {
            if (idt.contains(i)) {
                continue;
            }

            int index = Searching.linearSearch(books, i);

            assertTrue("If no book is found, linear search should return -1. Linear seach return the value " + index + " for a nonexistent book.", index == -1);
        }
    }

    @Test
    @Points("07-05.1")
    public void linearSearchFindsABookInUnorderedList() throws Throwable {
        ArrayList<Book> books = generateBooks(100);
        Collections.shuffle(books);
        for (int i = 0; i < books.size(); i++) {
            int index = Searching.linearSearch(books, books.get(i).getId());

            assertTrue("When the books were unordered, linear search didn't find the book searched for, even though the book was on the list. Try the search with a large number of books.", i == index);
        }
    }

    @Test
    @Points("07-05.2")
    public void binarySearchFindsTheBookWhenListSizeIsOne() throws Throwable {
        ArrayList<Book> books = generateBooks(1);
        int searchedIndex = books.get(0).getId();
        int index = Searching.binarySearch(books, searchedIndex);
        assertTrue("Binary search didn't find a book in list contaiting one book, even though the book was on the list. Try testing binary search with the following book:\n" + books.toString(), index == 0);
    }

    @Test
    @Points("07-05.2")
    public void binarySearchFindsTheBookOnAListOfTwoBooks() throws Throwable {
        ArrayList<Book> books = generateBooks(2);
        Collections.sort(books, (k1, k2) -> k1.getId() - k2.getId());

        for (int i = 0; i < books.size(); i++) {
            int index = Searching.binarySearch(books, books.get(i).getId());
            assertTrue("Binary search didn't find a book in list contaiting two books, even though the book was on the list. Try testing binary search with the following books:\n" + books.toString(), index == i);
        }
    }

    @Test
    @Points("07-05.2")
    public void binarySearchFindsTheBookFromAListOfThreeBooks() throws Throwable {
        ArrayList<Book> books = generateBooks(3);
        Collections.sort(books, (k1, k2) -> k1.getId() - k2.getId());

        for (int i = 0; i < books.size(); i++) {
            int index = Searching.binarySearch(books, books.get(i).getId());
            assertTrue("Binary search didn't find a book in list contaiting three books, even though the book was on the list. Try testing binary search with the following books:\n" + books.toString(), index == i);
        }
    }

    @Test
    @Points("07-05.2")
    public void binarySearchFindsTheBookFromAListOfFiveBooks() throws Throwable {
        ArrayList<Book> books = generateBooks(5);
        Collections.sort(books, (k1, k2) -> k1.getId() - k2.getId());

        for (int i = 0; i < books.size(); i++) {
            int index = Searching.binarySearch(books, books.get(i).getId());
            assertTrue("Binary search didn't find a book in list contaiting five books, even though the book was on the list. Try testing binary search with the following books:\n" + books.toString(), index == i);
        }
    }

    @Test
    @Points("07-05.2")
    public void binarySearchFindsTheBookFromAListOf100Books() throws Throwable {
        ArrayList<Book> books = generateBooks(100);
        Collections.sort(books, (k1, k2) -> k1.getId() - k2.getId());

        for (int i = 0; i < books.size(); i++) {
            int index = Searching.binarySearch(books, books.get(i).getId());
            assertTrue("Binary search didn't find a book in list contaiting five books, even though the book was on the list. Try testing binary search with a large number of books", index == i);
        }
    }

    @Test
    @Points("07-05.2")
    public void binarySearchDoesNotFindBooksFromAnUnorderedList() throws Throwable {
        ArrayList<Book> books = generateBooks(100);
        Collections.shuffle(books);

        int found = 0;
        for (int i = 0; i < books.size(); i++) {
            int index = Searching.binarySearch(books, books.get(i).getId());

            if (index == i) {
                found++;
            }
        }

        assertFalse("Binary search shouldn't work with an unordered list. Currently binary search works in an unsorted list", found > 25);
    }

    @Test
    @Points("07-05.2")
    public void binarySearchIsFasterThanLinearSearch() throws Throwable {
        ArrayList<Book> books = generateBooks(10000);
        Collections.sort(books, (k1, k2) -> k1.getId() - k2.getId());

        int searched = 10000001;
        long bSearchStart = System.nanoTime();
        int binarySearchId = Searching.binarySearch(books, searched);
        long bSearchEnd = System.nanoTime();
        assertTrue("When binary search does not find what was searched for, it must return -1", binarySearchId == -1);
        long lSearchStart = System.nanoTime();
        int linearSearchId = Searching.linearSearch(books, searched);
        long lSearchEnd = System.nanoTime();
        assertTrue("When linear search does not find what was searched for, it must return -1", linearSearchId == -1);

        long bSearchTime = bSearchEnd - bSearchStart;
        long lSearchTime = lSearchEnd - lSearchStart;

        assertTrue("When there are 10000 books to search, and the searched book is not found, binary search should be a lot faster than linear search. Current this isn't so", bSearchTime * 2 < lSearchTime);
    }

    private static ArrayList<Book> generateBooks(int montako) {

        Set<Integer> ids = new TreeSet<>();
        Random rnd = new Random();
        while (ids.size() < montako) {
            ids.add(rnd.nextInt(1000000));
        }

        ArrayList<Book> books = new ArrayList<>();
        for (Integer id : ids) {
            books.add(new Book(id, "name " + id));
        }

        return books;
    }
}
