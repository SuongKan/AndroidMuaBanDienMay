using DoAN_LapTrinhDiDong.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DoAN_LapTrinhDiDong.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LoaiSanPhamController : Controller
    {
        QL_DienMayContext db;
        public LoaiSanPhamController(QL_DienMayContext db)
        {
            this.db = db;
        }
        // GET: api/LoaiSanPham
        [HttpGet]
        public JsonResult Get()
        {
            Dictionary<string, List<LoaiSanPham>> data = new Dictionary<string,
           List<LoaiSanPham>>();
            var furnitures = db.LoaiSanPhams.ToList();
            data.Add("loaisanphams", furnitures);
            return new JsonResult(data);
        }


        //VD
        //GET api/LoaiSanPham/1
        [HttpGet("{id}")]
        public JsonResult Get(string id)
        {
            Dictionary<string, List<SanPham>> data = new Dictionary<string,
           List<SanPham>>();
            var listFurniture = db.SanPhams.Where(f => f.MaLoai == id);

            data.Add("sanphams", listFurniture.ToList());
            return new JsonResult(data);
        }
    }
}
