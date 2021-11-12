using System;
using System.Collections.Generic;

#nullable disable

namespace DoAN_LapTrinhDiDong.Models
{
    public partial class TaiKhoan
    {
        public TaiKhoan()
        {
            HoaDons = new HashSet<HoaDon>();
        }

        public int MaTk { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public string Email { get; set; }
        public string DiaChi { get; set; }
        public string GioiTinh { get; set; }

        public virtual ICollection<HoaDon> HoaDons { get; set; }
    }
}
