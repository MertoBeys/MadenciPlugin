# GitHub ile Otomatik Derleme (En Kolay ve Hızlı)

Bu yöntemle hiçbir şey yüklemenize gerek yok. Sadece GitHub'a yükleyin, otomatik JAR dosyası oluşacak.

## Adımlar:

### 1. GitHub Hesabı Oluşturun
- https://github.com adresine gidin
- Ücretsiz hesap oluşturun

### 2. Yeni Repository Oluşturun
- Sağ üstte "+" işaretine tıklayın
- "New repository" seçin
- Repository adı: `MadenciPlugin`
- Public seçin
- "Create repository" butonuna tıklayın

### 3. Dosyaları GitHub'a Yükleyin

**Yöntem A - GitHub Web Sitesinden (En Kolay):**
1. "Upload files" butonuna tıklayın
2. Aşağıdaki tüm dosyaları sürükleyip bırakın:
   - `src/` klasörü (tüm içeriğiyle)
   - `build.gradle`
   - `settings.gradle`
   - `gradlew.bat`
   - `.github/` klasörü
   - `pom.xml` (opsiyonel)
   - `README.md`
   - `KURULUM.md`
   - `GITHUB_KURULUM.md`
3. "Commit changes" butonuna tıklayın

**Yöntem B - Git Kullanarak:**
```bash
cd "c:\Users\mertk\OneDrive\Masaüstü\Yeni klasör (4)\MadenciPlugin"
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/KULLANICI_ADINIZ/MadenciPlugin.git
git push -u origin main
```
*(KULLANICI_ADINIZ kısmını kendi GitHub kullanıcı adınızla değiştirin)*

### 4. Otomatik Derleme

1. GitHub repository'nize gidin
2. "Actions" sekmesine tıklayın
3. "Build MadenciPlugin" workflow'u otomatik başlayacak
4. 1-2 dakika içinde derleme tamamlanacak
5. Yeşil ✓ işareti görünce başarılı demektir

### 5. JAR Dosyasını İndirin

1. Derleme tamamlandıktan sonra workflow'a tıklayın
2. En altta "Artifacts" bölümünü göreceksiniz
3. "MadenciPlugin" yazısına tıklayın
4. `MadenciPlugin-1.0.0.jar` dosyasını indirin

### 6. Minecraft Sunucusuna Kurulum

1. Sunucunuzu durdurun
2. `plugins` klasörüne gidin
3. İndirdiğiniz JAR dosyasını `plugins` klasörüne atın
4. Sunucuyu başlatın
5. Gerekli eklentiler:
   - ProtocolLib (zorunlu): https://www.spigotmc.org/resources/protocollib.1997/
   - Vault (opsiyonel): https://www.spigotmc.org/resources/vault.34315/

### 7. Kullanım

**GUI Menüsü:**
```
/madenci              - GUI menüsünü açar (tüm işlemler buradan yapılır)
```

**Komutlar (Alternatif):**
```
/madenci koy          - Madenci NPC koy
/madenci kaldir       - Madenci kaldır
/madenci stok         - Stok görüntüle
/madenci sat          - Stok sat
/madenci reload       - Config yeniden yükle
```

**GUI Menüsü Özellikleri:**
- Madenci Koy/Kaldır
- Stok Görüntüleme (itemler ve fiyatları)
- Stok Satma
- Seviye Bilgisi ve Seviye Atlama

## Sorun Giderme

**Derleme başlamıyor:**
- `.github/workflows/build.yml` dosyasının doğru yüklendiğinden emin olun
- Repository'nin public olduğundan emin olun

**Derleme hatası:**
- Actions sekmesinden hatayı kontrol edin
- Java dosyalarında syntax hatası olabilir

**JAR dosyası indirilemiyor:**
- Derlemenin tamamlandığından emin olun (yeşil ✓)
- Artifacts bölümünü kontrol edin

Bu yöntemle Maven veya Gradle yüklemenize gerek yok. Her şey GitHub'da otomatik gerçekleşir.
