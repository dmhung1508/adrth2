# CẤU TRÚC PROJECT - QUẢN LÝ SẢN PHẨM THỜI TRANG

## 📁 Cấu trúc thư mục

```
th2/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/th2/
│   │       │   ├── MainActivity.java          ✅ [Logic chính CRUD + Thống kê]
│   │       │   ├── Product.java               ✅ [Model sản phẩm]
│   │       │   └── ProductAdapter.java        ✅ [Adapter ListView]
│   │       │
│   │       ├── res/
│   │       │   ├── drawable/
│   │       │   │   ├── ic_ao.xml             ✅ [Icon áo - xanh lá]
│   │       │   │   ├── ic_quan.xml           ✅ [Icon quần - xanh dương]
│   │       │   │   └── ic_phukien.xml        ✅ [Icon phụ kiện - cam/tím]
│   │       │   │
│   │       │   ├── layout/
│   │       │   │   ├── activity_main.xml     ✅ [Màn hình chính]
│   │       │   │   ├── item_product.xml      ✅ [Layout item CardView]
│   │       │   │   └── dialog_product.xml    ✅ [Dialog thêm/sửa]
│   │       │   │
│   │       │   └── values/
│   │       │       ├── strings.xml           ✅ [Strings + categories array]
│   │       │       └── colors.xml            ✅ [Màu sắc]
│   │       │
│   │       └── AndroidManifest.xml           ✅ [Manifest]
│   │
│   └── build.gradle.kts                       ✅ [Dependencies + CardView]
│
├── README.md                                   ✅ [Tài liệu chính]
├── HUONG_DAN.md                               ✅ [Hướng dẫn chi tiết]
└── CAU_TRUC_PROJECT.md                        ✅ [File này]
```

## 🔍 Chi tiết từng file

### 1. MainActivity.java (243 dòng)
**Chức năng:**
- Quản lý danh sách sản phẩm
- Hiển thị ListView với adapter
- Dialog thêm/sửa sản phẩm
- Xóa sản phẩm với xác nhận
- Thống kê chi tiết
- Counter tự động cho mã sản phẩm

**Methods chính:**
```java
- onCreate()                    // Khởi tạo
- initViews()                  // Init views
- initData()                   // Init data với sample
- addSampleData()              // Thêm 3 sản phẩm mẫu
- setupListeners()             // Setup listeners
- showProductDialog()          // Dialog thêm/sửa
- showStatistics()             // Dialog thống kê
- updateProductCount()         // Cập nhật số lượng
- onEdit()                     // Interface edit
- onDelete()                   // Interface delete
```

### 2. Product.java (95 dòng)
**Chức năng:**
- Model class cho sản phẩm
- Getters/Setters đầy đủ
- Method getImageResource() - lấy icon theo loại
- Static method generateProductId() - sinh mã tự động

**Attributes:**
```java
- String productId           // A001, Q001, P001
- String name               // Tên sản phẩm
- String category           // Áo, Quần, Phụ kiện
- double price              // Giá bán
- int stockQuantity         // Số lượng tồn
- float rating              // Đánh giá 0-5
```

### 3. ProductAdapter.java (88 dòng)
**Chức năng:**
- Adapter cho ListView
- ViewHolder pattern
- Format giá VNĐ
- Interface callback cho Edit/Delete

**ViewHolder:**
```java
- ImageView imgProduct         // Hình sản phẩm
- TextView tvProductName       // Tên
- TextView tvProductCategory   // Loại
- TextView tvProductStock      // Số lượng
- TextView tvProductPrice      // Giá
- RatingBar ratingProduct      // Đánh giá
- ImageButton btnEdit          // Nút sửa
- ImageButton btnDelete        // Nút xóa
```

### 4. activity_main.xml
**Cấu trúc:**
```xml
LinearLayout (vertical)
├── AppBarLayout
│   └── MaterialToolbar (Quản lý Sản phẩm)
├── LinearLayout (horizontal - buttons)
│   ├── Button (Thêm SP)
│   └── Button (Thống kê)
├── View (divider)
├── TextView (Danh sách sản phẩm)
└── ListView (listViewProducts)
```

### 5. item_product.xml
**Cấu trúc:**
```xml
CardView
└── LinearLayout (horizontal)
    ├── ImageView (80x80dp - Icon sản phẩm)
    ├── LinearLayout (vertical - Thông tin)
    │   ├── TextView (Tên - 16sp bold)
    │   ├── TextView (Loại - 14sp gray)
    │   ├── TextView (Số lượng - 14sp gray)
    │   ├── TextView (Giá - 16sp bold red)
    │   └── RatingBar (small 5 sao)
    └── LinearLayout (vertical - Actions)
        ├── ImageButton (Edit)
        └── ImageButton (Delete)
```

### 6. dialog_product.xml
**Cấu trúc:**
```xml
LinearLayout (vertical)
├── TextView (Tiêu đề)
├── TextView (Mã SP - hidden khi thêm mới)
├── TextInputLayout + EditText (Tên)
├── TextView (Label) + Spinner (Loại)
├── TextInputLayout + EditText (Giá)
├── TextInputLayout + EditText (Số lượng)
├── TextView (Label) + RatingBar (Đánh giá)
└── LinearLayout (horizontal)
    ├── Button (Hủy)
    └── Button (Lưu)
```

### 7. Drawable Icons

#### ic_ao.xml
- Màu: #4CAF50 (Xanh lá)
- Icon: Shield/Shirt shape
- Size: 80x80dp

#### ic_quan.xml
- Màu: #2196F3 (Xanh dương)
- Icon: Circle/Pants shape
- Size: 80x80dp

#### ic_phukien.xml
- Màu: #FF9800, #FF5722, #9C27B0 (Cam, Đỏ, Tím)
- Icon: Triangle, Circle, Square
- Size: 80x80dp

### 8. strings.xml
```xml
- app_name: "Quản lý Thời trang"
- product_image: "Hình ảnh sản phẩm"
- edit_product: "Sửa sản phẩm"
- delete_product: "Xóa sản phẩm"
- product_categories array: ["Áo", "Quần", "Phụ kiện"]
```

### 9. build.gradle.kts
**Dependencies thêm vào:**
```kotlin
implementation("androidx.cardview:cardview:1.0.0")
```

## 📊 Phân tích dung lượng

| File | Dòng code | Chức năng chính |
|------|-----------|-----------------|
| MainActivity.java | ~243 | CRUD + Thống kê |
| Product.java | ~95 | Model |
| ProductAdapter.java | ~88 | Adapter |
| activity_main.xml | ~52 | Layout chính |
| item_product.xml | ~73 | Layout item |
| dialog_product.xml | ~92 | Layout dialog |
| **TỔNG CODE** | **~643** | **Hoàn chỉnh** |

## 🎯 Mapping với yêu cầu đề bài

### ✅ Layout (4 điểm)
- `activity_main.xml` - Màn hình chính
- `item_product.xml` - Item sản phẩm với CardView
- `dialog_product.xml` - Dialog thêm/sửa
- 3 icon drawable theo loại sản phẩm

### ✅ Thêm sản phẩm (2 điểm)
- Method: `showProductDialog(null, -1, false)`
- Spinner chọn loại
- RatingBar đánh giá
- Auto generate mã: `Product.generateProductId()`

### ✅ Sửa sản phẩm (1.5 điểm)
- Method: `showProductDialog(product, position, true)`
- Load dữ liệu hiện tại
- Disable Spinner (không đổi loại)

### ✅ Xóa sản phẩm (0.5 điểm)
- Method: `onDelete(product, position)`
- AlertDialog xác nhận
- Remove từ list

### ✅ Thống kê (2 điểm)
- Method: `showStatistics()`
- Tổng sản phẩm
- Đếm theo loại
- Sản phẩm rating cao nhất

## 🔄 Flow hoạt động

### Flow Thêm sản phẩm:
```
1. User nhấn "Thêm SP"
2. MainActivity.showProductDialog(null, -1, false)
3. Show dialog với form trống
4. User điền thông tin
5. btnSave.onClick() validate
6. Product.generateProductId(category, count)
7. new Product() → add vào productList
8. adapter.notifyDataSetChanged()
9. updateProductCount()
10. dialog.dismiss()
```

### Flow Sửa sản phẩm:
```
1. User nhấn btnEdit trên item
2. ProductAdapter.OnProductActionListener.onEdit()
3. MainActivity.showProductDialog(product, position, true)
4. Load dữ liệu vào form
5. Disable spinner
6. User sửa thông tin
7. btnSave.onClick() validate
8. Update product object
9. adapter.notifyDataSetChanged()
10. dialog.dismiss()
```

### Flow Xóa sản phẩm:
```
1. User nhấn btnDelete trên item
2. ProductAdapter.OnProductActionListener.onDelete()
3. Show AlertDialog xác nhận
4. User nhấn "Xóa"
5. productList.remove(position)
6. adapter.notifyDataSetChanged()
7. updateProductCount()
```

### Flow Thống kê:
```
1. User nhấn "Thống kê"
2. MainActivity.showStatistics()
3. Loop qua productList:
   - Đếm tổng
   - Đếm theo category
   - Tìm max rating
4. Format text thống kê
5. Show AlertDialog
```

## 🎨 Design Pattern

### MVC Pattern:
- **Model**: Product.java
- **View**: XML Layouts
- **Controller**: MainActivity.java

### ViewHolder Pattern:
- ProductAdapter sử dụng ViewHolder
- Tối ưu hiệu năng ListView

### Observer Pattern:
- Interface OnProductActionListener
- Callback từ Adapter → MainActivity

## 🔧 Công nghệ sử dụng

| Thành phần | Công nghệ |
|------------|-----------|
| Language | Java 11 |
| UI Framework | Android Views |
| List View | ListView + ArrayAdapter |
| Layout | LinearLayout, ConstraintLayout |
| Material | CardView, TextInputLayout |
| Input | EditText, Spinner, RatingBar |
| Dialog | AlertDialog |
| Format | NumberFormat |
| Pattern | MVC, ViewHolder, Observer |

## ✨ Điểm nổi bật

1. **Code clean** - Dễ đọc, có cấu trúc
2. **UI đẹp** - Material Design, CardView
3. **UX tốt** - Validation, confirmation
4. **Format chuẩn** - Giá VNĐ đúng format
5. **Auto-generate** - Mã sản phẩm tự động
6. **Sample data** - 3 sản phẩm mẫu
7. **Icon đẹp** - Vector drawable theo loại
8. **Thống kê đẹp** - Format với emoji và divider

## 🚀 Sẵn sàng chạy

✅ Tất cả file đã tạo  
✅ Dependencies đã thêm  
✅ Code đã test  
✅ Không có lỗi  
✅ Sample data có sẵn  
✅ Tài liệu đầy đủ  

**→ Chỉ cần Sync Gradle và Run!**

