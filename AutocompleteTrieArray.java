import java.util.*; //mengimpor semua kelas dalam paket 'java.util'

public class AutocompleteTrieArray {//definisikan kelas publik AutocompleteTrieArray yang merupakan kelas utama 
    static final int NUM_OF_CHARS = 128; //menentukan jumlah maksimum karakter yang dapat diwakili dalam trie, yaitu 128 (jumlah karakter ASCII)

    static class TrieNode {
        char data; //deklarasi variabel data
        TrieNode[] children = new TrieNode[NUM_OF_CHARS];//mendeklarasikan array children untuk menyimpan referensi ke anak-anak dari node ini. 
        //Array ini berukuran 128 sesuai dengan NUM_OF_CHARS.
        boolean isEnd = false;//deklarasikan variabel isEnd untuk menandai apakah node ini adalah akhir dari sebuah kata.

        TrieNode(char c) {
            data = c;//konstruktor untuk kelas 'TrieNode' yang menginisialisasi node dengan karakter 'c'
        }
    }

    static class Trie {
        TrieNode root = new TrieNode(' ');

        void insert(String word) {//definisikan metode insert untuk menambahkan kata ke dalam trie.
            TrieNode node = root;//inisialisasi node sementara yang mulai dari root.
            for (char ch : word.toCharArray()) {
                ch = Character.toLowerCase(ch); //mengubah uppercase menjadi lowercase
                if (node.children[ch] == null)
                    node.children[ch] = new TrieNode(ch);
                node = node.children[ch];
            }
            node.isEnd = true;
        }

        List<String> autocomplete(String prefix) {//mendefinisikan metode autocomplete untuk menemukan semua kata yang dimulai dengan prefix tertentu
            TrieNode node = root;
            List<String> res = new ArrayList<>();
            for (char ch : prefix.toCharArray()) {
                ch = Character.toLowerCase(ch); // Convert to lowercase
                node = node.children[ch];
                if (node == null)
                    return new ArrayList<>();//Jika node anak tidak ada, kembalikan daftar kosong.
            }
            helper(node, res, prefix.substring(0, prefix.length() - 1));//memanggil helper untuk mengumpulkan semua kata yang sesuai.
            return res;
        }

        void helper(TrieNode node, List<String> res, String prefix) {//mendefinisikan metode bantu helper
            if (node == null)
                return;//Jika node saat ini null, keluar dari metode.

            
                
            if (node.isEnd)//jika node merupakan akhir kata...
                res.add(prefix + node.data);//tambahkan kata ke daftar hasil.
            for (TrieNode child : node.children)
                if (child != null)//Iterasi melalui semua anak dari node saat ini. Jika anak tidak null, panggil helper secara rekursif.
                    helper(child, res, prefix + node.data);
        }
    }

    public static void main(String[] args) {//Mendefinisikan metode main, titik awal dari program.
        Trie t = new Trie();//Membuat objek Trie baru.
        t.insert("KampasRemDepanHondaAbsolutRevo");//Menambahkan beberapa kata ke dalam trie menggunakan metode insert.
        t.insert("KampasRemBelakangHondaAbsolutRevo");
        t.insert("KampasKoplingHondaAbsolutRevo");
        t.insert("KampasRemDepanBeat110CC");
        t.insert("KampasKoplingSetBeat110CC");
		



        Scanner scanner = new Scanner(System.in);//Membuat objek Scanner untuk membaca input dari pengguna.

        

        System.out.println("Start typing to autocomplete. Type 'reset' to start a new search or 'exit' to quit.");
        while (true) {//Memulai loop utama program untuk terus menerima input dari pengguna.
            StringBuilder currentInput = new StringBuilder();//Menginisialisasi StringBuilder dan memulai loop untuk membaca input.
            while (true) {
                System.out.print("Input: ");
                String input = scanner.nextLine();//Meminta pengguna untuk memasukkan teks dan membaca input.
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;//Jika pengguna mengetik "exit", tampilkan pesan keluar, tutup scanner, dan keluar dari program.
                } else if (input.equalsIgnoreCase("reset")) {
                    System.out.println("Starting a new search...");
                    break;//Jika pengguna mengetik "reset", tampilkan pesan dan keluar dari loop saat ini untuk memulai pencarian baru.
                }
                currentInput.append(input);
                List<String> suggestions = t.autocomplete(currentInput.toString());
                System.out.println("Suggestions: " + suggestions);//Tambahkan input ke currentInput, panggil metode autocomplete dengan input saat ini,
            }
        }
    }
}
