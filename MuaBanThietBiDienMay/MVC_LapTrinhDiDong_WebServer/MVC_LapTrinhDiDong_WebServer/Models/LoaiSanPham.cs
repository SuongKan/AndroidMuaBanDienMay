using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MVC_LapTrinhDiDong_WebServer.Models
{
    public class Loaisanpham
    {
        [JsonProperty("maLoai")]
        public string MaLoai { get; set; }

        [JsonProperty("tenLoai")]
        public string TenLoai { get; set; }

        [JsonProperty("image")]
        public string Image { get; set; }

        [JsonProperty("sanPhams")]
        public List<object> SanPhams { get; set; }
    }

    public class RootLoaiSanPham
    {
        [JsonProperty("loaisanphams")]
        public List<Loaisanpham> Loaisanphams { get; set; }
    }
}