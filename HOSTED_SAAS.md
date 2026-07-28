# Hosted "Proof-of-Reserves" SaaS - Kısa İş Planı

Bu dosya, projeyi hosted bir servis olarak sunmanın temel planını ve fiyatlandırma önerilerini içerir.

## Ürün Tanımı
"Proof-of-Reserves as a Service" — kurumsal müşteriler için merkle doğrulamalarını barındıran, zamanlanmış raporlar ve API erişimi sunan bir servis.

## Fiyatlandırma Önerisi
- Freemium: küçük hesaplar için sınırlı doğrulama (ör. aylık 5 rapor ücretsiz)
- Starter: 99 USD / ay — temel otomatik doğrulama + 7/30 günlük saklama
- Business: 499 USD / ay — gelişmiş raporlama, API erişimi, 30 günlük saklama
- Enterprise: özel fiyat — SLA, özel entegrasyon ve on-premise destek

## Teknik Gereksinimler
- Otomasyon: zamanlanmış işler (cron), veri çekme entegrasyonları (SFTP/API)
- Güvenlik: TLS, IAM, audit logging, veri şifreleme (at-rest ve in-transit)
- Ölçek: containerized deployment (Kubernetes), monitoring, backup
- Ödeme altyapısı: Stripe/PayPal/Invoice desteği

## GDPR & Uyumluluk
- Müşteri verileri işleniyorsa sözleşme ve veri işleme ekleri (DPA) gereklidir.
- Kurumsal müşteriler için SOC2 / ISO27001 gereksinimleri göz önünde bulundurulmalı.

## Satış & Pazarlama
- Hedef müşteri: kripto borsaları, custody sağlayıcıları, fintech firmaları
- Pilot proje başlatma: 30-60 günlük POC teklifi
- Demo ve kurulum teklifi için iletişim: ismailpinrin0@gmail.com (güncelleyin)
