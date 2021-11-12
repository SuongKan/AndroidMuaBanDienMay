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
    public class TaiKhoanController : ControllerBase
    {
        QL_DienMayContext db;
        public TaiKhoanController(QL_DienMayContext db)
        {
            this.db = db;
        }
        // GET: api/TaiKhoan
        [HttpGet]
        public JsonResult Get()
        {
            Dictionary<string, List<TaiKhoan>> data = new Dictionary<string,
           List<TaiKhoan>>();
            var listTaikhoans = db.TaiKhoans.ToList();
            data.Add("taikhoans", listTaikhoans);
            return new JsonResult(data);
        }

        //VD
        /*GET api/TaiKhoan?username=suongkan&&matkhau=1234567*/
        [HttpGet("{username}/{matkhau}")]
        public JsonResult Get(string username,string matkhau)
        {
            Dictionary<string, List<TaiKhoan>> data = new Dictionary<string,
           List<TaiKhoan>>();
            var listTaikhoans = db.TaiKhoans.Where(tk => tk.Username.ToString() == username && tk.Password.ToString() == matkhau);
            data.Add("taikhoans", listTaikhoans.ToList());
            return new JsonResult(data);
        }

        [HttpPost]
        public JsonResult Post([FromBody] TaiKhoan taiKhoan)
        {
            Dictionary<string, string> data = new Dictionary<string, string>();
            try
            {
                db.Add(taiKhoan);
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
        [HttpPut("UpdateUser/{id}")]
        public JsonResult Put(int id,[FromBody] TaiKhoan taiKhoan)
        {
            Dictionary<string, string> data = new Dictionary<string, string>();
            try
            {
                var taiKhoan1 = db.TaiKhoans.Where(tk => tk.MaTk == id).FirstOrDefault<TaiKhoan>();
                if (taiKhoan1 != null)
                {
                    taiKhoan1.Email = taiKhoan.Email;
                    taiKhoan1.DiaChi = taiKhoan.DiaChi;
                    taiKhoan1.GioiTinh = taiKhoan.GioiTinh;
                    db.SaveChanges();
                    data.Add("Message", "OK");
                    return new JsonResult(data);
                }
                else {
                    data.Add("Message", "Lỗi");
                    return new JsonResult(data);
                }
                    
            }
            catch (Exception ex)
            {
                data.Add("Error", ex.Message);
                return new JsonResult(data);
            }
        }
        [HttpPut("UpdatePassword/{id}")]
        public JsonResult PutPassword(int id, [FromBody] TaiKhoan taiKhoan)
        {
            Dictionary<string, string> data = new Dictionary<string, string>();
            try
            {
                var taiKhoan1 = db.TaiKhoans.Where(tk => tk.MaTk == id).FirstOrDefault<TaiKhoan>();
                if (taiKhoan1 != null)
                {
                    taiKhoan1.Password = taiKhoan.Password;
                    db.SaveChanges();
                    data.Add("Message", "OK");
                    return new JsonResult(data);
                }
                else
                {
                    data.Add("Message", "Lỗi");
                    return new JsonResult(data);
                }

            }
            catch (Exception ex)
            {
                data.Add("Error", ex.Message);
                return new JsonResult(data);
            }
        }
    }
}
