# IoT_FE_AndriodStudio
-App chỉ bao gồm 1 database(db) tên demo (DBhelper , DbTemp,DbHumi) Ứng với light,temp và humi (bảng light mình dùng để test graph nên chưa gọi api mqtt)
+Lưu ý :
- mỗi lần chỉnh sửa thêm bảng vào database thì phải tăng thông tin biến Data_version(ca 3 file DB) lên, nếu không chương trình sẽ bị crash
- Mình có tạo 1 api deleteAlldata dùng để debug sau mỗi lần first test, sau khi demo thì nên commment nếu ko sẽ mất hết dữ liệu nhập vào bảng
* NHỮNG THỨ CẦN THIẾT TRONG ACTIVITY
- Để làm mục module 4 , cần tạo thêm 1 activity lưu những gì mình thao tác tương tự cái bảng Log trên adafruit.
- Mỗi mục cảm biến temp , humi cần phải tạo ra 1 FE list data. Nếu xem 1 list data thì mỗi thông số current sẽ hiện ra số cuối cùng(hiện tại số current chúng ta lấy thẳng từ adafruit) => t cần khi mở app lên nó sẽ hiện thông số luôn không phải đợi dữ liệu lên mới lấy
- Ngoài ra cần phải giới hạn số dữ liệu  mỗi cảm biến, ví dụ như FIFO 100 dữ liệu hay là dữ liệu quá 3 ngày tính từ hiện tại sẽ tự động delete(giống như graph dashboard tren adafruit).<\br>
- --------------------------------------------------------------
<\br><\br><\br>
Update 14/4 <\br>
- Thông qua sqlite , đã tạo được các activity hiện data của từng bảng temp và humi
- Cập nhật được dữ liệu mới nhất của bảng
- Vấn đề còn lại là quản lý giới hạn dữ liệu data ( dự định sẽ set timestamp trong vòng 1 ngày kể từ khi dữ liệu được tạo)
