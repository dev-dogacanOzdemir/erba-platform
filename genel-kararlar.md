# Microservice Security & Authorization Architecture Decision Record

## Amaç

Bu doküman, erba-platform içerisindeki mikroservis mimarisinde authentication, authorization ve security altyapısının nasıl konumlandırılacağına dair alınan mimari kararları ve bunların gerekçelerini içerir.

Bu kararlar aşağıdaki hedefler göz önünde bulundurularak alınmıştır:

* Mikroservis bağımsızlığını korumak
* Geliştirme hızını düşürmemek
* Gereksiz erken abstraction oluşturmamak
* İleride servis sayısı arttığında bakım maliyetini kontrol altında tutmak
* Merkezi auth yapısını sürdürülebilir şekilde kurmak
* Future-proof bir yapı oluşturmak
* Department-based ve workflow-based authorization sistemlerine uygun bir temel hazırlamak

---

# Genel Mimari Yaklaşım

Sistem aşağıdaki ana domain ayrımlarıyla ilerleyecektir:

## 1. Auth Domain

Sorumluluklar:

* Register
* Login
* JWT generation
* Refresh token management
* Logout
* Forgot password
* Reset password
* Authentication lifecycle
* Session management

Bu domain tamamen `auth-service` sorumluluğundadır.

### Kritik Not

Auth-service sadece kimlik doğrulama ve oturum yönetiminden sorumludur.

Aşağıdaki bilgiler auth-service içerisinde business data olarak tutulmayacaktır:

* Department
* Employee profile
* HR bilgileri
* Organizasyon yapısı
* Approval chain
* İş akışı verileri

---

## 2. Identity Domain

Sorumluluklar:

* Çalışan bilgileri
* Department bilgileri
* Pozisyon bilgileri
* Employee profile
* Organization ilişkileri
* Sensitive employee data

Bu domain tamamen `identity-service` sorumluluğundadır.

---

## 3. Business Modules

İleride eklenecek modüller:

* Purchase Request Management
* Task Management
* Department-specific modules
* Approval workflows
* Internal process systems

Bu servisler kendi business authorization kurallarını kendileri yönetecektir.

---

# Auth & Identity Relationship

## Nihai Flow

### Kullanıcı Oluşturma

1. ADMIN login olur
2. ADMIN identity-service üzerinden employee oluşturur
3. Gerekirse auth account ayrıca oluşturulur
4. identity.users.auth_user_id alanı auth.users.id ile bağlanır

### Kritik Karar

```text
Auth account ≠ Identity profile
```

Bu iki yapı birbirinden bağımsız domain olarak tasarlanacaktır.

Sebep:

* Mikroservis bağımsızlığı
* HR süreçleri ile authentication süreçlerinin ayrılması
* Future scalability
* Domain separation
* Yetki ve organizasyon değişikliklerinin auth lifecycle'dan ayrılması

---

# JWT Architecture Decision

## Seçilen Yaklaşım

### Access Token

* JWT access token kullanılacak
* Stateless authentication modeli kullanılacak
* Access token Authorization header üzerinden taşınacak

```text
Authorization: Bearer ACCESS_TOKEN
```

### Refresh Token

* HttpOnly cookie içerisinde tutulacak
* Sadece auth-service tarafından okunacak
* Sadece auth-service refresh token bilecek

```text
refresh_token cookie → sadece auth-service
```

---

# Neden Refresh Token Diğer Servislerde Kullanılmıyor?

Aşağıdaki yaklaşım bilinçli olarak REDDEDİLMİŞTİR:

```text
identity-service → refresh token cookie okur
purchase-service → refresh token ile authorization yapar
```

Sebep:

* Distributed session chaos
* Session management'in dağılması
* Güvenlik sınırlarının bozulması
* Refresh token'ın gereğinden fazla servis tarafından bilinmesi
* Session lifecycle yönetiminin zorlaşması

Refresh token sadece session renewal içindir.

Authorization için kullanılmayacaktır.

---

# Token Validation Strategy

## Değerlendirilen Alternatifler

### Alternatif 1

```text
Her servis auth-service'e token validate sorusu sorar
```

Örnek:

```text
identity-service → auth-service /validate-token
```

### Bu Yaklaşım REDDEDİLDİ

Sebep:

* Tight coupling
* Auth-service runtime bottleneck olur
* Auth-service down olursa tüm sistem etkilenir
* Yüksek latency
* Mikroservis bağımsızlığının bozulması
* Runtime dependency oluşması

---

## Seçilen Yaklaşım

### Local JWT Validation

Her servis JWT access token'ı local olarak validate edecek.

Avantajları:

* Servis bağımsızlığı
* Düşük latency
* Auth-service runtime dependency yok
* Better scalability
* Better fault isolation
* Daha doğru microservice yaklaşımı

---

# Shared Security Module Decision

## Karar

Minimal ortak security altyapısı `shared-kernel/security` altında oluşturulacak.

---

## Shared Module İçerisinde OLACAK Yapılar

```text
shared-kernel/security
```

İçerik:

* JwtProperties
* JwtTokenValidator
* JwtAuthenticationFilter
* AuthenticatedUser
* CurrentUserProvider
* Role helper utilities

Bu yapılar tüm servislerde ortak kullanılacak.

---

## Shared Module İçerisinde OLMAYACAK Yapılar

Aşağıdaki yapılar shared-kernel içerisine taşınmayacak:

* LoginService
* RegisterService
* Refresh token management
* Cookie management
* Forgot password
* Reset password
* User persistence
* Auth repositories
* SecurityConfig
* Business authorization rules

Sebep:

Bunlar auth-service domain logic'idir.

Shared-kernel içerisine alınmaları mikroservis bağımsızlığını bozabilir.

---

# SecurityConfig Decision

## Karar

Her servis kendi SecurityConfig yapısını yönetecek.

### Shared SecurityConfig kullanılmayacak.

Sebep:

Authorization business-specific'tir.

Örnek:

## identity-service

```text
POST /identity-users → ADMIN
```

## purchase-service

```text
/create-request → EMPLOYEE
/approve-request → MANAGER
```

## task-service

```text
/assign-task → MANAGER
/update-own-task → EMPLOYEE
```

Bu nedenle authorization kuralları servis bazlı yönetilecek.

---

# API Gateway Decision

## Şu Anki Karar

### API Gateway hemen kurulmayacak.

Sebep:

* Erken complexity artışı
* Geliştirme hızının düşmesi
* Şu anki servis sayısı için gereksiz abstraction

---

## Gelecekteki Plan

Servis sayısı arttığında:

```text
3-4+ servis
```

Spring Cloud Gateway değerlendirilecek.

---

## Gateway Sorumlulukları

Gateway aşağıdaki görevleri üstlenecek:

* Routing
* CORS
* Rate limiting
* Request logging
* Correlation IDs
* Basic JWT validation
* General route protection

---

## Gateway İçerisinde OLMAYACAK Yapılar

Gateway aşağıdaki business authorization kararlarını vermeyecek:

* Department approval logic
* Resource ownership
* Approval workflows
* Employee-specific permissions
* Sensitive data access

Bu kararlar ilgili business service içerisinde kalacak.

---

# Authorization Strategy

## Genel Yetkilendirme

JWT içerisinde:

* userId
* role

bilgileri tutulacak.

Örnek:

```json
{
  "sub": "user-id",
  "role": "ADMIN"
}
```

---

## Business Authorization

Department veya workflow bazlı authorization bilgileri JWT içerisinde tutulmayacak.

Örnek:

* department
* manager hierarchy
* approval chain
* team memberships

Sebep:

Bu veriler sık değişebilir.

JWT içerisine konursa:

```text
stale authorization data
```

problemi oluşabilir.

Bu bilgiler ilgili business service veya identity-service üzerinden yönetilecek.

---

# HS256 vs RS256 Decision

## Şu Anki Karar

### HS256 kullanılacak.

Sebep:

* Daha hızlı geliştirme
* Daha düşük complexity
* Küçük ekip için uygun
* Daha kolay setup
* Current scale için yeterli

---

## Gelecek Planı

Servis sayısı arttığında:

### RS256 + JWKS endpoint değerlendirilecek.

Hedef yapı:

```text
Auth-service → private key ile sign
Diğer servisler → public key ile validate
```

---

## Neden Şimdilik RS256 Kullanılmıyor?

Sebep:

* Gereksiz erken complexity
* Key rotation yönetimi gerektirir
* JWKS infrastructure gerektirir
* Development velocity düşebilir

---

# Uzun Vadeli Hedef Mimari

```text
Frontend
   ↓
Gateway (opsiyonel)
   ↓
Microservices
   ↓
Shared security validation
   ↓
Auth-service (issuer)
```

---

# Final Architecture Principles

## Sistem aşağıdaki prensiplere göre geliştirilecektir:

### 1. Auth-service merkezi issuer olacak

### 2. Refresh token sadece auth-service tarafından bilinecek

### 3. Access token self-contained olacak

### 4. Servisler tokenları local validate edecek

### 5. Shared security sadece validation altyapısı sağlayacak

### 6. Business authorization servislerde kalacak

### 7. SecurityConfig shared olmayacak

### 8. Gateway ileride eklenecek şekilde tasarım açık tutulacak

### 9. Department/workflow authorization JWT içerisine gömülmeyecek

### 10. Mikroservis bağımsızlığı korunacak

---

# Özet Karar

## Şu an uygulanacak mimari:

```text
✔ Auth-service → authentication lifecycle
✔ Shared-kernel/security → JWT validation infrastructure
✔ Her servis → kendi authorization kuralları
✔ Refresh token → sadece auth-service
✔ Access token → tüm servislerde validation
✔ HS256 → şimdilik
✔ Gateway → daha sonra
✔ Business authorization → servis bazlı
```

Bu yapı:

* Geliştirme hızını korur
* Mikroservis mantığını bozmaz
* Future-proof kalır
* Over-engineering oluşturmaz
* Yetki sistemlerinin ileride genişletilmesini kolaylaştırır
* Purchase/task/workflow sistemlerine uygun temel sağlar
