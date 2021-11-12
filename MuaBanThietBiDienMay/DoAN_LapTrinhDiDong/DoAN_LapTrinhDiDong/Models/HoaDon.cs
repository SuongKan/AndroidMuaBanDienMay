using System;
using System.Collections.Generic;

#nullable disable

namespace DoAN_LapTrinhDiDong.Models
{
    public partial class HoaDon
    {
        public HoaDon()
        {
            Chitiethds = new HashSet<Chitiethd>();
        }

        public int MaHd { get; set; }
        public DateTime? NgayLap { get; set; }
        public int? MaTk { get; set; }
        public string DiaChi { get; set; }
        public int? TongTien { get; set; }

        public virtual TaiKhoan MaTkNavigation { get; set; }
        public virtual ICollection<Chitiethd> Chitiethds { get; set; }
    }
}
