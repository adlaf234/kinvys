import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class MainPitza {
    public static void main(String[] args) {

        System.out.println("Menghubungi Server API Pizza Hut (Simulasi)...");
        System.out.println("--------------------------------------------------");

        try {
            // 1. URL API Endpoint
            String endpoint = "https://httpbin.org/base64/W3sibWVudSI6Ik1lYXQgTG92ZXJzIiwiaGFyZ2EiOiJScCA5NS4wMDAifSwgeyJtZW51IjoiU3VwZXIgU3VwcmVtZSIsImhhcmdhIjoiUnAgOTAuMDAwIn0sIHsibWVudSI6IkFtZXJpY2FuIEZhdm91cml0ZSIsImhhcmdhIjoiUnAgODUuMDAwIn0sIHsibWVudSI6IkxpbW8gUGl6emEiLCJoYXJnYSI6IlJwIDI4MC4wMDAifV0=";

            URL url = new URL(endpoint);

            // 2. Buka koneksi internet
            HttpURLConnection koneksi = (HttpURLConnection) url.openConnection();
            koneksi.setRequestMethod("GET");
            koneksi.connect();

            // 3. Cek status (200 = sukses)
            if (koneksi.getResponseCode() == 200) {

                // 4. Membaca data JSON mentah dari server
                Scanner pembaca = new Scanner(url.openStream());
                StringBuilder jsonMentah = new StringBuilder();

                while (pembaca.hasNext()) {
                    jsonMentah.append(pembaca.nextLine());
                }
                pembaca.close();

                String dataJson = jsonMentah.toString();
                System.out.println("\n✅ KONEKSI BERHASIL!\n");

                System.out.println("----- DAFTAR MENU & HARGA PIZZA HUT -----");

                // 5. PARSING MANUAL
                // Tahap A: Bersihkan simbol JSON
                String dataBersih = dataJson
                        .replace("[", "").replace("]", "")
                        .replace("{", "").replace("}", "")
                        .replace("\"", "");

                // Tahap B: Pisahkan antar menu
                String[] daftarMenu = dataBersih.split(", ");

                // Tahap C: Ambil nama & harga
                for (String item : daftarMenu) {

                    String[] detail = item.split(", ");

                    String namaMenu = detail[0].replace("menu:", "").trim();
                    String hargaMenu = detail[1].replace("harga:", "").trim();

                    System.out.println("🍕 " + namaMenu + "\t- " + hargaMenu);
                }

                System.out.println("--------------------------------------------------");

            } else {
                System.out.println("❌ Gagal terhubung. Kode Error: " + koneksi.getResponseCode());
            }

        } catch (Exception e) {
            System.out.println("❌ ERROR SISTEM: Pastikan laptop terhubung ke internet!");
            System.out.println("Pesan: " + e.getMessage());
        }
    }
}
