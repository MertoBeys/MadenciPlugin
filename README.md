# MadenciPlugin

Minecraft için Madenci NPC eklentisi. Madenleri otomatik olarak toplayan ve satan bir NPC sistemi.

## Özellikler

- **Madenci NPC**: Madenleri otomatik olarak toplayan NPC oluşturma
- **Otomatik Kırma**: Maden bloklarını otomatik kırma
- **Otomatik Toplama**: Yere düşen maden itemlerini otomatik toplama
- **Otomatik Satış**: Stok dolunca otomatik satış
- **Seviye Sistemi**: Madenci seviyeleri ve kapasite artışı
- **Vergi Sistemi**: Satışlardan vergi kesintisi
- **Claim Desteği**: Skyblock ve claim sistemleri ile uyumluluk

## Kurulum

1. `pom.xml` ile projeyi derleyin:
   ```bash
   mvn clean package
   ```

2. Oluşan JAR dosyasını sunucunuzun `plugins` klasörüne atın

3. Sunucuyu yeniden başlatın

4. Gerekli eklentiler:
   - ProtocolLib (zorunlu)
   - Vault (opsiyonel - ekonomi için)

## Komutlar

- `/madenci koy` - Madenci NPC koyar
- `/madenci kaldir` - Madenci NPC kaldırır
- `/madenci stok` - Madenci stokunu görüntüler
- `/madenci sat` - Stoktaki itemleri satar
- `/madenci reload` - Config dosyalarını yeniden yükler

## Yetkiler

- `madenci.koy` - Madenci koyma
- `madenci.kaldir` - Madenci kaldırma
- `madenci.stok` - Stok görüntüleme
- `madenci.sat` - Stok satma
- `madenci.reload` - Config yeniden yükleme
- `madenci.autosell` - Otomatik satış
- `madenci.autobreak` - Otomatik kırma
- `madenci.autocollect` - Otomatik toplama

## Config Ayarları

### config.yml

- `useClaims`: Claim sistemi kullanımı
- `requireOnline`: Çalışması için oyuncu online olmalı mı
- `autoSell`: Otomatik satış ayarları
- `autoBreak`: Otomatik kırma ayarları
- `autoCollect`: Otomatik toplama ayarları
- `tax`: Vergi ayarları
- `MinerLevels`: Seviye ve kapasite ayarları

### items.yml

Maden itemleri ve fiyatları buradan ayarlanabilir. Örnek:
```yaml
Items:
  demir:
    material: iron_ingot
    price: 2.0
  elmas:
    material: diamond
    price: 15.0
```

## Desteklenen Madenler

- Demir (Iron)
- Altın (Gold)
- Elmas (Diamond)
- Zümrüt (Emerald)
- Kömür (Coal)
- Kızıltaş (Redstone)
- Lapis Lazuli
- Bakır (Copper)
- Obsidyen
- Kuvars

## Geliştirme

Bu proje Spigot API 1.13+ ile uyumludur. Java 17 veya üzeri gerektirir.

## Lisans

Bu proje özgürce kullanılabilir ve değiştirilebilir.
