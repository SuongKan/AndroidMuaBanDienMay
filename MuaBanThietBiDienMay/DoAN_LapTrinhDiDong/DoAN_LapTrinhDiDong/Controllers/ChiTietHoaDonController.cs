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
    public class ChiTietHoaDonController : ControllerBase
    {
        QL_DienMayContext db;
        public ChiTietHoaDonController(QL_DienMayContext db)
        {
            this.db = db;
        }
        [HttpGet]
        public JsonResult Get()
        {
            Dictionary<string, List<Chitiethd>> data = new Dictionary<string,
           List<Chitiethd>>();
            var listCTHDs = db.Chitiethds.ToList();
            data.Add("chiTietHDs", listCTHDs);
            return new JsonResult(data);
        }
        [HttpGet("{MaHD}")]
        public JsonResult Get(int mahd)
        {
            Dictionary<string, List<CTHD>> data = new Dictionary<string,
           List<CTHD>>();
            var items = db.Chitiethds
                .Join(db.SanPhams, cthd => cthd.MaSp, sp => sp.MaSanPham, (cthd, sp) => new { cthd, sp })
                .Join(db.LoaiSanPhams, a => a.sp.MaLoai, loai => loai.MaLoai, (a, loai) => new { a, loai })
                .Where(ct=>ct.a.cthd.MaHd==mahd)
                .Select(ct => new
                {
                    MaCTHD=ct.a.cthd.MaCthd,
                    MaHD = ct.a.cthd.MaHd,
                    TenSP=ct.a.sp.TenSanPham,
                    LoaiSp=ct.loai.TenLoai,
                    GiaTien=ct.a.sp.GiaTien,
                    HinhAnh=ct.a.sp.Image,
                    SoLuong=ct.a.cthd.SoLuong
                }).OrderBy(ct=>ct.MaCTHD).ToList() ;
            List<CTHD> cTHDs = new List<CTHD>();
            foreach (var obj in items)
            {
                CTHD cTHD = new CTHD();

                cTHD.MaCTHD = obj.MaCTHD;
                cTHD.MaHd = obj.MaHD;
                cTHD.TenSP = obj.TenSP;
                cTHD.LoaiSP = obj.LoaiSp;
                cTHD.DonGia = obj.GiaTien;
                cTHD.HinhAnh = obj.HinhAnh;
                cTHD.SoLuong = obj.SoLuong;

                cTHDs.Add(cTHD);
            }
            data.Add("chiTietHDs", cTHDs.ToList());
            return new JsonResult(data);
        }
        [HttpPost]
        public JsonResult Post([FromBody] Chitiethd chitiethd)
        {
            Dictionary<string, string> data = new Dictionary<string, string>();
            try
            {
                db.Add(chitiethd);
                db.SaveChanges();
                data.Add("Message", "OK");
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
