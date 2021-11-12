using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MVC_LapTrinhDiDong_WebServer.Models
{
    public class Sanpham
    {
        public int maSanPham { get; set; }
        public string tenSanPham { get; set; }
        public int soLuongTon { get; set; }
        public int giaTien { get; set; }
        public string moTa { get; set; }
        public string maLoai { get; set; }
        public string image { get; set; }
        public object maLoaiNavigation { get; set; }
        [JsonProperty("chitiethds")]
        public List<object> chitiethds { get; set; }
    }

    public class Root
    {
        [JsonProperty("sanphams")]
        public List<Sanpham> sanphams { get; set; }
    }
}