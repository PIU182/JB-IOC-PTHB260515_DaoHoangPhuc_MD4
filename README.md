# MICRO SERVICES - QUẢN LÝ SINH VIÊN VÀ KHÓA HỌC



## 1. Công nghệ

Java 17, Spring Boot 3.5.14, Spring Cloud 2025.0.3,

Gradle, Eureka, Spring Cloud Gateway, OpenFeign, PostgreSQL.



## 2. Kiến trúc

POSTMAN -> Gateway :8080 -> Student :8081 -> student_db

                       |-> Course :8082  -> course_db

Student --OpenFeign--> Course

Eureka Server :8761: Gateway, Student và Course đăng ký.



## 3. Cách hoạt động

POST /api/students/enroll {studentId, courseId}:

- Student kiểm tra sinh viên tồn tại, chưa đăng ký trùng.

- Feign GET Course để xem capacity > 0.

- Gọi Course reserve để trừ 1 chỗ an toàn.

- Thêm courseId vào mảng enrolled_courses của sinh viên.

- Trả kết quả và số chỗ còn lại.



## 4. Cách chạy

1. Tạo student_db và course_db trên PostgreSQL.

2. Chạy Eureka 8761 -> Course 8082 -> Student 8081 -> Gateway 8080.

3. Truy cập http://localhost:8761 xem 3 service UP.

4. Postman GET /api/courses/1 và POST /api/students/enroll qua 8080.



## 5. Task 3

### Câu 1: Service Discovery là gì? Vì sao không ghi cứng IP/Port?

Eureka giống một danh bạ dịch vụ: các service đăng ký tên và

địa chỉ của mình. Gateway dùng lb://STUDENT-SERVICE hoặc

lb://COURSE-SERVICE để tìm nơi gửi request. Nếu service đổi

port/IP hoặc chạy thêm instance, Gateway không cần sửa route.



### Câu 2: Scale Student Service mà không đổi Gateway?

Chạy thêm instance Student Service dùng port khác, ví dụ 8083,

nhưng cùng tên student-service và đăng ký Eureka. Gateway vẫn

route qua lb://STUDENT-SERVICE; LoadBalancer chọn instance.

Các instance cùng quản lý student_db và không giữ session cục bộ.



### Câu 3: OpenFeign và Kafka trong bài này?

OpenFeign đồng bộ: Student gọi HTTP tới Course rồi chờ kết quả,

dễ viết và biết ngay còn chỗ hay không; nhược điểm là phụ thuộc

Course đang hoạt động. Kafka bất đồng bộ: gửi event để xử lý sau,

thích hợp lượng request lớn nhưng phải cài broker, producer,

consumer và thiết kế cách nhận kết quả. Em chọn Feign vì đề cho

phép và bài thi 180 phút cần phản hồi đăng ký ngay.



## 6. Giới hạn

Student và Course có hai database độc lập. Trừ chỗ Course và

lưu Student chưa phải một giao dịch xuyên service. Nếu save

Student thất bại sau khi Course đã trừ chỗ, có thể lệch dữ liệu.

Hệ thống thực tế cần Saga/compensation, idempotency và retry.