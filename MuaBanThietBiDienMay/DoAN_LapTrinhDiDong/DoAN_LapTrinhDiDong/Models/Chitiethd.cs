using System;
using System.Collections.Generic;

#nullable disable

namespace DoAN_LapTrinhDiDong.Models
{
    public partial class Chitiethd
    {
        public int MaCthd { get; set; }
        public int? MaHd { get; set; }
        public int? MaSp { get; set; }
        public int? DonGia { get; set; }
        public int? SoLuong { get; set; }

        public virtual HoaDon MaHdNavigation { get; set; }
        public virtual SanPham MaSpNavigation { get; set; }
    }
}
