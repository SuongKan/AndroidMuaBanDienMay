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
    public class HoaDonController : ControllerBase
    {
        QL_DienMayContext db;
        public HoaDonController(QL_DienMayContext db)
        {
            this.db = db;
        }
        [HttpGet]
        public JsonResult Get()
        {
            Dictionary<string, List<HoaDon>> data = new Dictionary<string,
           List<HoaDon>>();
            var hoaDons = db.HoaDons.ToList();

            data.Add("hoadons", hoaDons);
            return new JsonResult(data);
        }
        [HttpGet("GetHoaDonnByID/{TK_id}")]
        public JsonResult GetHoaDonnByID(int TK_id)
        {
            Dictionary<string, List<HoaDon>> data = new Dictionary<string,
           List<HoaDon>>();
            var hoaDons = db.HoaDons.Where(hd => hd.MaTk == TK_id);
            data.Add("hoadons", hoaDons.ToList());
            return new JsonResult(data);
        }
        [HttpGet("GetLastHoaDon/{TK_id}")]
        public JsonResult GetLastHoaDon(int TK_id)
        {
            Dictionary<string, List<HoaDon>> data = new Dictionary<string,
           List<HoaDon>>();
            var hoaDons = db.HoaDons.OrderBy(hd => hd.MaHd).LastOrDefault(hd => hd.MaTk == TK_id);
            return new JsonResult(hoaDons);
        }
        [HttpPost]
        public JsonResult Post([FromBody] HoaDon hoaDon)
        {
            Dictionary<string, string> data = new Dictionary<string, string>();
            try
            {
                db.Add(hoaDon);
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
