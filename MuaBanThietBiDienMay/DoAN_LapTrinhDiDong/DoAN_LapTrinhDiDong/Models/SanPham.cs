using System;
using System.Collections.Generic;

#nullable disable

namespace DoAN_LapTrinhDiDong.Models
{
    public partial class SanPham
    {
        public SanPham()
        {
            Chitiethds = new HashSet<Chitiethd>();
        }

        public int MaSanPham { get; set; }
        public string TenSanPham { get; set; }
        public int? SoLuongTon { get; set; }
        public int? GiaTien { get; set; }
        public string MoTa { get; set; }
        public string MaLoai { get; set; }
        public string Image { get; set; }

        public virtual LoaiSanPham MaLoaiNavigation { get; set; }
        public virtual ICollection<Chitiethd> Chitiethds { get; set; }
    }
}
