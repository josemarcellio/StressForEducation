# StressForEducation

StressForEducation adalah alat untuk menguji ketahanan server dengan mengirimkan banyak permintaan HTTP GET secara bersamaan, original author @grafov

## Requirements

- Java 8+

## Cara Menggunakan

```bash
java -jar StressForEducation.jar <url> <durasi>
```

**Contoh:**

- Stress Test selama 60 detik:

```bash
java -jar target/StressForEducation.jar https://website.com 60
```

- Stress Test tanpa durasi waktu:

```bash
java -jar target/StressForEducation.jar https://website.com
```

## PERINGATAN!

**Jangan gunakan alat ini untuk tindakan kriminal yang melanggar hukum!** Alat ini hanya untuk tujuan edukasi!
