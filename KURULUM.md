# MadenciPlugin Kurulum Rehberi

## Maven ile Derleme (Önerilen)

### 1. Maven Yükleme

**Windows için:**
1. https://maven.apache.org/download.cgi adresinden Apache Maven'i indirin
2. İndirdiğiniz zip dosyasını bir klasöre çıkarın (örn: C:\Program Files\Maven)
3. Sistem ortam değişkenlerine ekleyin:
   - Windows arama çubuğuna "ortam değişkenleri" yazın
   - "Ortam değişkenlerini düzenle"ye tıklayın
   - "Sistem değişkenleri" bölümünde "Path" değişkenini düzenleyin
   - Maven'in bin klasörünü ekleyin (örn: C:\Program Files\Maven\apache-maven-3.9.5\bin)
4. Komut satırını açın ve `mvn --version` yazarak doğrulayın

### 2. Projeyi Derleme

```bash
cd "c:\Users\mertk\OneDrive\Masaüstü\Yeni klasör (4)\MadenciPlugin"
mvn clean package
```

Derleme tamamlandığında `target` klasöründe `MadenciPlugin-1.0.0.jar` dosyası oluşacaktır.

## IntelliJ IDEA ile Derleme (Alternatif)

### 1. IntelliJ IDEA'yı Açın
- File → Open → MadenciPlugin klasörünü seçin
- Proje Maven projesi olarak tanınacak

### 2. Projeyi Derleyin
- Sağ taraftaki Maven panelini açın
- MadenciPlugin → Lifecycle → clean → package'a tıklayın
- Veya üst menüden Build → Build Project

### 3. JAR Dosyasını Bulun
- target klasöründe `MadenciPlugin-1.0.0.jar` dosyası oluşacak

## Minecraft Sunucusuna Kurulum

### 1. Gerekli Eklentiler
- **ProtocolLib** (Zorunlu): https://www.spigotmc.org/resources/protocollib.1997/
- **Vault** (Opsiyonel - Ekonomi için): https://www.spigotmc.org/resources/vault.34315/

### 2. Plugin Yükleme
1. Sunucuyu durdurun
2. `plugins` klasörüne gidin
3. `MadenciPlugin-1.0.0.jar` dosyasını `plugins` klasörüne atın
4. Sunucuyu başlatın

### 3. İlk Kurulum
- Sunucu başladığında config dosyaları otomatik oluşturulacak
- `plugins/MadenciPlugin/config.yml` dosyasından ayarları düzenleyebilirsiniz
- `plugins/MadenciPlugin/items.yml` dosyasından maden fiyatlarını ayarlayabilirsiniz

### 4. Kullanım
```
/madenci koy          - Madenci NPC koy
/madenci kaldir       - Madenci kaldır
/madenci stok         - Stok görüntüle
/madenci sat          - Stok sat
/madenci reload       - Config yeniden yükle
```

## Sorun Giderme

**Maven bulunamadı hatası:**
- Maven doğru yüklendiğinden emin olun
- Path değişkenlerini kontrol edin
- Komut satırını yeniden başlatın

**Derleme hatası:**
- Java 17+ yüklü olduğundan emin olun
- İnternet bağlantınızın olduğundan emin olun (bağımlılıklar indirilecek)

**Plugin çalışmıyor:**
- ProtocolLib yüklü mü kontrol edin
- Sunucu versiyonu 1.13+ mı kontrol edin
- Konsol hatalarını kontrol edin
