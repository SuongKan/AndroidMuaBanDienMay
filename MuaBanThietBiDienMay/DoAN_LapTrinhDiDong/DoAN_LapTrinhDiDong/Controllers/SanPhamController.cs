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
    public class SanPhamController : ControllerBase
    {
        QL_DienMayContext db;
        public SanPhamController(QL_DienMayContext db)
        {
            this.db = db;
        }
        // GET: api/SanPham
        [HttpGet]
        public JsonResult Get()
        {
            Dictionary<string, List<SanPham>> data = new Dictionary<string,
           List<SanPham>>();
            var listsanPhams = db.SanPhams.ToList();
            data.Add("sanphams", listsanPhams);
            return new JsonResult(data);
        }
        //VD
        //GET api/SanPham/1
        [HttpGet("{id}")]
        public JsonResult Get(int id)
        {
            Dictionary<string, List<SanPham>> data = new Dictionary<string,
           List<SanPham>>();
            var sanPham = db.SanPhams.Where(f => f.MaSanPham == id);

            data.Add("sanphams", sanPham.ToList());
            return new JsonResult(data);
        }
        [HttpPost]
        public JsonResult Post([FromBody] SanPham sanPham)
        {
            Dictionary<string, string> data = new Dictionary<string, string>();
            try
            {
                db.Add(sanPham);
                db.SaveChanges();
                data.Add("Message", "Insert OK");
                var listsanPhams = db.SanPhams.ToList();
                return new JsonResult(data);
            }
            catch (Exception ex)
            {
                data.Add("Error", ex.Message);
                return new JsonResult(data);
            }
        }
        [HttpPut("{id}")]
        public JsonResult Put(int id, [FromBody] SanPham sanPham)
        {
            Dictionary<string, string> data = new Dictionary<string, string>();
            try
            {
                var sanpham = db.SanPhams.Where(sp => sp.MaSanPham == id).FirstOrDefault();
                var loaisp = db.LoaiSanPhams.Where(loai => loai.MaLoai == sanPham.MaLoai).FirstOrDefault();
                if (loaisp == null)
                {
                    data.Add("Message", "Update OK");
                    return new JsonResult(data);
                }
                else if (sanpham != null)
                {
                    sanpham.TenSanPham = sanPham.TenSanPham;
                    sanpham.GiaTien = sanPham.GiaTien;
                    sanpham.SoLuongTon = sanPham.SoLuongTon;
                    sanpham.MoTa = sanPham.MoTa;
                    sanpham.MaLoai = sanPham.MaLoai;
                    sanpham.Image = sanPham.Image;
                    db.SaveChanges();
                    data.Add("Message", "Update OK");
                    return new JsonResult(data);
                }
                else
                {
                    data.Add("Message", "Không tồn tại mã sản phẩm này");
                    return new JsonResult(data);
                }

            }
            catch (Exception ex)
            {
                data.Add("Error", ex.Message);
                return new JsonResult(data);
            }
        }
        [HttpDelete("{id}")]
        public JsonResult Delete(int id)
        {
            Dictionary<string, string> data = new Dictionary<string, string>();
            try {
                var sanpham = db.SanPhams.Where(sp => sp.MaSanPham == id).FirstOrDefault();
                db.SanPhams.Remove(sanpham);
                db.SaveChanges();
                data.Add("Message", "Delete OK");
                return new JsonResult(data);
            }
            catch (Exception ex)
            {
                data.Add("Error", ex.Message);
                return new JsonResult(data);
            }
        }
    }
}
