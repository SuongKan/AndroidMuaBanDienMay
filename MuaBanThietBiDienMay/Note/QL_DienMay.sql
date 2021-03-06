USE [QL_DienMay]
GO
/****** Object:  Table [dbo].[CHITIETHD]    Script Date: 5/24/2021 1:04:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CHITIETHD](
	[MaCTHD] [int] IDENTITY(1,1) NOT NULL,
	[MaHD] [int] NULL,
	[MaSP] [int] NULL,
	[DonGia] [int] NULL,
	[SoLuong] [int] NULL,
 CONSTRAINT [PK_ChiTietHoaDon] PRIMARY KEY CLUSTERED 
(
	[MaCTHD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[HoaDon]    Script Date: 5/24/2021 1:04:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HoaDon](
	[MaHD] [int] IDENTITY(1,1) NOT NULL,
	[NgayLap] [date] NULL,
	[MaTK] [int] NULL,
	[DiaChi] [nchar](100) NULL,
	[TongTien] [int] NULL,
 CONSTRAINT [PK_HoaDon] PRIMARY KEY CLUSTERED 
(
	[MaHD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[LoaiSanPham]    Script Date: 5/24/2021 1:04:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LoaiSanPham](
	[MaLoai] [char](2) NULL,
	[TenLoai] [ntext] NULL,
	[Image] [text] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[SanPham]    Script Date: 5/24/2021 1:04:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SanPham](
	[MaSanPham] [int] IDENTITY(1,1) NOT NULL,
	[TenSanPham] [ntext] NULL,
	[SoLuongTon] [int] NULL,
	[GiaTien] [int] NULL,
	[MoTa] [ntext] NULL,
	[MaLoai] [char](2) NULL,
	[Image] [ntext] NULL,
 CONSTRAINT [PK_SanPham] PRIMARY KEY CLUSTERED 
(
	[MaSanPham] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[TaiKhoan]    Script Date: 5/24/2021 1:04:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaiKhoan](
	[MaTK] [int] IDENTITY(1,1) NOT NULL,
	[Username] [text] NULL,
	[Password] [text] NULL,
	[Email] [text] NULL,
	[DiaChi] [ntext] NULL,
	[GioiTinh] [text] NULL,
 CONSTRAINT [PK_TaiKhoan] PRIMARY KEY CLUSTERED 
(
	[MaTK] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET IDENTITY_INSERT [dbo].[HoaDon] ON 

INSERT [dbo].[HoaDon] ([MaHD], [NgayLap], [MaTK], [DiaChi], [TongTien]) VALUES (1, CAST(0x69250B00 AS Date), 1, N'DA NANG                                                                                             ', 0)
INSERT [dbo].[HoaDon] ([MaHD], [NgayLap], [MaTK], [DiaChi], [TongTien]) VALUES (6, CAST(0x60430B00 AS Date), 1, N'DA NANG                                                                                             ', 0)
INSERT [dbo].[HoaDon] ([MaHD], [NgayLap], [MaTK], [DiaChi], [TongTien]) VALUES (7, CAST(0x76430B00 AS Date), 1, N'TP.HCM                                                                                              ', 0)
SET IDENTITY_INSERT [dbo].[HoaDon] OFF
INSERT [dbo].[LoaiSanPham] ([MaLoai], [TenLoai], [Image]) VALUES (N'AC', N'Máy lạnh', N'MayLanh.jpg')
INSERT [dbo].[LoaiSanPham] ([MaLoai], [TenLoai], [Image]) VALUES (N'DT', N'Ðiện thoại', N'Phone.jpg')
INSERT [dbo].[LoaiSanPham] ([MaLoai], [TenLoai], [Image]) VALUES (N'GD', N'Điện gia dụng', N'dienGiaDung.jpg')
INSERT [dbo].[LoaiSanPham] ([MaLoai], [TenLoai], [Image]) VALUES (N'MG', N'Máy giặt', N'MayGiat.jpg')
INSERT [dbo].[LoaiSanPham] ([MaLoai], [TenLoai], [Image]) VALUES (N'PC', N'Máy tính', N'MayTinh.jpg')
INSERT [dbo].[LoaiSanPham] ([MaLoai], [TenLoai], [Image]) VALUES (N'SP', N'Loa', N'Karaoke.jpg')
INSERT [dbo].[LoaiSanPham] ([MaLoai], [TenLoai], [Image]) VALUES (N'TL', N'Tủ  lạnh', N'TuLAnh.jpg')
INSERT [dbo].[LoaiSanPham] ([MaLoai], [TenLoai], [Image]) VALUES (N'TV', N'Tivi', N'TV.jpg')
SET IDENTITY_INSERT [dbo].[SanPham] ON 

INSERT [dbo].[SanPham] ([MaSanPham], [TenSanPham], [SoLuongTon], [GiaTien], [MoTa], [MaLoai], [Image]) VALUES (1, N'Tủ lạnh Samsung 360 lít', 20, 13990000, N'Thiết kế mạnh mẽ, cứng cáp.
Công nghệ Digital Inverter giúp làm lạnh nhanh, tiết kiệm điện, duy trì nhiệt độ phù hợp.
5 chế độ chuyển đổi với 2 dàn lạnh tiện lợi tiết kiệm điện năng.
Tiện lợi hơn với tính năng làm đá tự động và lấy nước lạnh bên ngoài không cần mở tủ.', N'TL', N'TuLanh_2.jpg')
INSERT [dbo].[SanPham] ([MaSanPham], [TenSanPham], [SoLuongTon], [GiaTien], [MoTa], [MaLoai], [Image]) VALUES (2, N'Tủ Lạnh SHARP 165 Lít', 20, 3840000, N'Tủ Lạnh SHARP Inverter 165 Lít. Tự động điều chỉnh máy nén để giảm thiểu mức tiêu thụ điện năng lên đến 32%, Vận hành êm ái.Hạn chế tiếng ồn. Với công nghệ J-Tech Inverter, thiết bị thay đổi công suất làm lạnh ở 36 cấp độ, nhiều hơn công nghệ Inverter thông thường với chỉ 7 cấp độ. Điều này giúp thiết bị điều chỉnh cấp độ vận hành để đáp ứng nhu cầu làm lạnh tương ứng hiệu quả, êm ái và hạn chế gây ra tiếng ồn. ', N'TL', N'TuLanh_3.png')
INSERT [dbo].[SanPham] ([MaSanPham], [TenSanPham], [SoLuongTon], [GiaTien], [MoTa], [MaLoai], [Image]) VALUES (3, N'Smart Tivi Samsung 85*', 21, 71890000, N'Công Nghệ Quantum Dot Hiển Thị 100% Dải Sắc Màu Công nghệ Quantum Dot hiển thị 100% dải màu với độ chân thực sống động và rõ nét, cho bạn thưởng thức từng khung hình mang màu sắc cuộc sống, tuyệt đẹp ở mọi mức độ sáng. Rực Rỡ Sắc Màu, Bền Bỉ Dài Lâu Với Công Nghệ Quantum Dot.', N'TV', N'TV_2.png')
INSERT [dbo].[SanPham] ([MaSanPham], [TenSanPham], [SoLuongTon], [GiaTien], [MoTa], [MaLoai], [Image]) VALUES (4, N'Smart Tivi Neo Samsung 75* ', 20, 179900000, N'Smart Tivi Neo Samsung 75* mang thiết kế tinh tế cùng với cạnh viền siêu mỏng tạo nên một tuyệt tác TV giúp nâng tầm trải nghiệm xem của bạn thêm phần hoàn hảo và chân thực. Cạnh viền gần như vô hình giúp bạn hoàn toàn tập trung trải nghiệm nội dung ưa thích mà không bị xao nhãng.', N'TV', N'TV_3.png')
INSERT [dbo].[SanPham] ([MaSanPham], [TenSanPham], [SoLuongTon], [GiaTien], [MoTa], [MaLoai], [Image]) VALUES (5, N'Smart TV Samsung 43* ', 20, 12900000, N'Tivi 43 inch, thiết kế không viền 3 cạnh·thanh mảnh, độ phân giải Ultra HD 4K sắc nét gấp 4 lần Full HD.
Hiển thị 100% dải màu bằng công nghệ màn hình chấm lượng tử Quantum Dot.
Tăng cường độ tương phản và độ chính xác của màu sắc bằng công nghệ đèn nền Dual Led và Quantum HDR.
Hình ảnh rõ nét với sắc đen sâu thẳm, sắc trắng tinh khiết qua công nghệ Supreme UHD Dimming.
Hệ điều hành Tizen OS đơn giản, dễ sử dụng và kho ứng dụng phong phú.
Chiếu màn hình điện thoại lên tivi qua Tap View và AirPlay 2, xem nhiều nội dung cùng lúc với tính năng Multi View (Trình chiếu đa màn hình).', N'TV', N'TV_1.png')
INSERT [dbo].[SanPham] ([MaSanPham], [TenSanPham], [SoLuongTon], [GiaTien], [MoTa], [MaLoai], [Image]) VALUES (6, N'Tủ lạnh LG 315 lít', 20, 7990000, N'Vận hành êm, tiết kiệm điện hiệu quả với công nghệ Inverter.
Giúp thực phẩm tươi ngon đồng đều nhờ hệ thống làm lạnh đa chiều.
Khử mùi, diệt khuẩn với công nghệ Nano Carbon.
Làm lạnh hiệu quả thực phẩm ở cửa tủ và khay cửa thông qua công nghệ DoorCooling+.
Rau quả tươi lâu với ngăn cân bằng độ ẩm hiện đại.
Theo dõi tình trạng lỗi tủ lạnh thông qua tính năng Smart Diagnosis.
Dễ dàng lấy thực phẩm bên trong nhờ khay kéo trợ lực PULL-OUT TRAY. ', N'TL', N'TuLanh_1.jpg')
INSERT [dbo].[SanPham] ([MaSanPham], [TenSanPham], [SoLuongTon], [GiaTien], [MoTa], [MaLoai], [Image]) VALUES (7, N'Máy lạnh Samsung 1 HP', 20, 10990000, N'Công suất làm lạnh 1 HP, dành cho phòng có diện tích dưới 15 mét vuông.
Luồng khí lạnh lý tưởng, tiết kiệm điện với công nghệ Wind-Free với 23.000 lỗ nhỏ ở mặt trước.
Tiết kiệm điện, làm lạnh nhanh với động cơ Digital Inverter và chế độ Eco.
Kháng khuẩn, chống nấm mốc hiệu quả cùng bộ lọc Care Filter. 
Tạo không khí thoáng đãng, dễ chịu khi trời ẩm ướt với chế độ hút ẩm.
Làm lạnh hiệu quả, độ bền máy cao với Auto Clean.
Màn hình LED hiển thị nhiệt độ tiện lợi.', N'AC', N'MayLanh_1.jpg')
INSERT [dbo].[SanPham] ([MaSanPham], [TenSanPham], [SoLuongTon], [GiaTien], [MoTa], [MaLoai], [Image]) VALUES (8, N' Máy lạnh Daiki 1 HP', 20, 10590000, N'Tiết kiệm điện hơn với công nghệ Inverter và chế độ Econo.
Lọc sạch không khí với màng lọc Enzyme Blue hoặc PM 2.5 (tuỳ chọn).
Tính năng chống ẩm mốc Mold Proof giúp dàn lạnh sạch sẽ, hạn chế mùi hôi.
Làm lạnh dễ chịu, đưa luồng hơi lạnh đi xa với thiết kế Coanda.
Dàn tản nhiệt được bảo vệ bởi 2 lớp chống ăn mòn.
Êm ái, tránh làm phiền bạn và người thân với chế độ hoạt động êm.
Tính năng Powerful làm lạnh nhanh để bạn không phải chờ lâu.
Công suất 1 HP dành cho phòng dưới 15 m2.
Mức chỉnh nhiệt 0.5 độ C, lựa chọn nhiệt độ dễ dàng hơn. ', N'AC', N'MayLanh_2.jpg')
SET IDENTITY_INSERT [dbo].[SanPham] OFF
SET IDENTITY_INSERT [dbo].[TaiKhoan] ON 

INSERT [dbo].[TaiKhoan] ([MaTK], [Username], [Password], [Email], [DiaChi], [GioiTinh]) VALUES (1, N'suongkan', N'1234567', N'suongkan@gmail.com', N'TP.HCM', N'Female')
SET IDENTITY_INSERT [dbo].[TaiKhoan] OFF
ALTER TABLE [dbo].[HoaDon] ADD  CONSTRAINT [DF_HoaDon_TongTien]  DEFAULT ((0)) FOR [TongTien]
GO
ALTER TABLE [dbo].[CHITIETHD]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietHoaDon_HoaDon] FOREIGN KEY([MaHD])
REFERENCES [dbo].[HoaDon] ([MaHD])
GO
ALTER TABLE [dbo].[CHITIETHD] CHECK CONSTRAINT [FK_ChiTietHoaDon_HoaDon]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_TaiKhoan] FOREIGN KEY([MaTK])
REFERENCES [dbo].[TaiKhoan] ([MaTK])
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_TaiKhoan]
GO
