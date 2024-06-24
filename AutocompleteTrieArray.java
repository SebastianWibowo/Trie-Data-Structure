import java.util.*;
import java.io.*;

public class AutocompleteTrieArray {
    static final int NUM_OF_CHARS = 128; // Menentukan jumlah maksimum karakter yang dapat diwakili dalam trie, yaitu 128 (jumlah karakter ASCII)

    static class TrieNode {
        char data; // Deklarasi variabel data
        TrieNode[] children = new TrieNode[NUM_OF_CHARS]; // Mendeklarasikan array children untuk menyimpan referensi ke anak-anak dari node ini.
        boolean isEnd = false; // Deklarasikan variabel isEnd untuk menandai apakah node ini adalah akhir dari sebuah kata.

        TrieNode(char c) {
            data = c; // Konstruktor untuk kelas 'TrieNode' yang menginisialisasi node dengan karakter 'c'
        }
    }

    static class Trie {
        TrieNode root = new TrieNode(' ');

        void insert(String word) { // Definisikan metode insert untuk menambahkan kata ke dalam trie.
            TrieNode node = root; // Inisialisasi node sementara yang mulai dari root.
            for (char ch : word.toCharArray()) {
                ch = Character.toLowerCase(ch); // Mengubah uppercase menjadi lowercase
                if (node.children[ch] == null)
                    node.children[ch] = new TrieNode(ch);
                node = node.children[ch];
            }
            node.isEnd = true;
        }

        List<String> autocomplete(String prefix) { // Mendefinisikan metode autocomplete untuk menemukan semua kata yang dimulai dengan prefix tertentu
            TrieNode node = root;
            List<String> res = new ArrayList<>();
            for (char ch : prefix.toCharArray()) {
                ch = Character.toLowerCase(ch); // Convert to lowercase
                node = node.children[ch];
                if (node == null) {
                    return new ArrayList<>(); // Jika node anak tidak ada, kembalikan daftar kosong.
                }
            }
            helper(node, res, prefix.substring(0, prefix.length() - 1)); // Memanggil helper untuk mengumpulkan semua kata yang sesuai.
            return res;
        }

        void helper(TrieNode node, List<String> res, String prefix) { // Mendefinisikan metode bantu helper
            if (node == null)
                return; // Jika node saat ini null, keluar dari metode.

            if (node.isEnd) // Jika node merupakan akhir kata...
                res.add(prefix + node.data); // Tambahkan kata ke daftar hasil.
            for (TrieNode child : node.children)
                if (child != null) // Iterasi melalui semua anak dari node saat ini. Jika anak tidak null, panggil helper secara rekursif.
                    helper(child, res, prefix + node.data);
        }
    }

    public static void main(String[] args) { // Mendefinisikan metode main, titik awal dari program.
        Trie t = new Trie(); // Membuat objek Trie baru.

        // Membaca file dan memasukkan kata-kata ke dalam trie
        try (BufferedReader br = new BufferedReader(new FileReader("sukucadang.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                t.insert(line.trim()); // Memasukkan setiap baris (kata) dari file ke dalam trie
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in); // Membuat objek Scanner untuk membaca input dari pengguna.

        System.out.println("Silahkan masukkan nama barang yang ingin dicari misal (kabel), jika ingin keluar dari pencarian, ketik 'exit'");
        StringBuilder currentInput = new StringBuilder(); // Menginisialisasi StringBuilder untuk menyimpan input saat ini.
        
        while (true) { // Memulai loop utama program untuk terus menerima input dari pengguna.
            System.out.print("Input: ");
            String input = scanner.nextLine(); // Meminta pengguna untuk memasukkan teks dan membaca input.
            
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                scanner.close();
                return; // Jika pengguna mengetik "exit", tampilkan pesan keluar, tutup scanner, dan keluar dari program.
            }

            currentInput.append(input);
            List<String> suggestions = t.autocomplete(currentInput.toString());
            if (suggestions.isEmpty()) {
                System.out.println("Nama sparepart tidak ditemukan, silahkan coba lagi dengan memasukan nama sparepart yang sesuai");
            } else {
                System.out.println("Suggestions: " + suggestions);
            }

            currentInput.setLength(0); // Reset currentInput untuk pencarian berikutnya.
        }
    }
}
