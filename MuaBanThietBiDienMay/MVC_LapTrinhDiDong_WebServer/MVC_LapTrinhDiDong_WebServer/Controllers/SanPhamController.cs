using MVC_LapTrinhDiDong_WebServer.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;

namespace MVC_LapTrinhDiDong_WebServer.Controllers
{
    public class SanPhamController : Controller
    {
        // GET: SanPham
        string Baseurl = "http://localhost:5000/";
        public async Task<ActionResult> Index()
        {
            List<Sanpham> SpInfo = new List<Sanpham>();
            using (var client = new HttpClient())
            {
                //Passing service base url
                client.BaseAddress = new Uri(Baseurl);
                client.DefaultRequestHeaders.Clear();
                //Define request data format
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                //Sending request to find web api REST service resource GetAllEmployees using HttpClient
                HttpResponseMessage Res = await client.GetAsync("api/SanPham");
                //Checking the response is successful or not which is sent using HttpClient
                if (Res.IsSuccessStatusCode)
                {
                    //Storing the response details recieved from web api
                    var EmpResponse = Res.Content.ReadAsStringAsync().Result;
                    //Deserializing the response recieved from web api and storing into the Employee list
                    SpInfo = JsonConvert.DeserializeObject<Root>(EmpResponse).sanphams;
                }
                //returning the employee list to view
                return View(SpInfo);
            }
        }
        [HttpGet]
        public async Task<ActionResult> Add()
        {
            List<Loaisanpham> LoaiInfo = new List<Loaisanpham>();
            using (var client = new HttpClient())
            {
                //Passing service base url
                client.BaseAddress = new Uri(Baseurl);
                client.DefaultRequestHeaders.Clear();
                //Define request data format
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                //Sending request to find web api REST service resource GetAllEmployees using HttpClient
                HttpResponseMessage Res = await client.GetAsync("api/LoaiSanPham");
                //Checking the response is successful or not which is sent using HttpClient
                if (Res.IsSuccessStatusCode)
                {
                    //Storing the response details recieved from web api
                    var EmpResponse = Res.Content.ReadAsStringAsync().Result;
                    //Deserializing the response recieved from web api and storing into the Employee list
                    LoaiInfo = JsonConvert.DeserializeObject<RootLoaiSanPham>(EmpResponse).Loaisanphams;
                }
                ViewBag.loai = new SelectList(LoaiInfo.ToList(), "MaLoai", "TenLoai");
                return View();
            }
        }
        [HttpPost]
        [ValidateInput(false)]
        public async Task<ActionResult> Add(Sanpham sanpham)
        {
            using (var client = new HttpClient()) {
                client.BaseAddress = new Uri(Baseurl);
                client.DefaultRequestHeaders.Clear();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                HttpResponseMessage response = await client.PostAsJsonAsync("api/SanPham", sanpham);
                if (Res.IsSuccessStatusCode)
                {
                    return RedirectToAction("Index");
                }
                TempData["SuccessMessage"] = "Insert Successfully";
                return View("Create");
            }
        }
        [HttpGet]
        public async Task<ActionResult> Edit(int id)
        {
            List<Loaisanpham> LoaiInfo = new List<Loaisanpham>();
            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri(Baseurl);
                client.DefaultRequestHeaders.Clear();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                HttpResponseMessage Res = await client.GetAsync("api/LoaiSanPham");
                //Checking the response is successful or not which is sent using HttpClient
                if (Res.IsSuccessStatusCode)
                {
                    //Storing the response details recieved from web api
                    var EmpResponse = Res.Content.ReadAsStringAsync().Result;
                    //Deserializing the response recieved from web api and storing into the Employee list
                    LoaiInfo = JsonConvert.DeserializeObject<RootLoaiSanPham>(EmpResponse).Loaisanphams;
                }
                ViewBag.loai = new SelectList(LoaiInfo.ToList(), "MaLoai", "TenLoai");

                HttpResponseMessage Res2 = await client.GetAsync("api/SanPham/"+id);
                var SpResponse = Res2.Content.ReadAsStringAsync().Result;
                var sp = JsonConvert.DeserializeObject<Root>(SpResponse).sanphams;
                return View(sp[0]);
            }
        }
        [HttpPost]
        public async Task<ActionResult> Update(Sanpham sanpham)
        {
            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri(Baseurl);
                client.DefaultRequestHeaders.Clear();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                HttpResponseMessage response = await client.PutAsJsonAsync("api/SanPham/"+sanpham.maSanPham, sanpham);
                TempData["SuccessMessage"] = "Updated Successfully";
                return RedirectToAction("Index");
            }
        }
        public async Task<ActionResult> Delete(int id)
        {
            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri(Baseurl);
                client.DefaultRequestHeaders.Clear();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                HttpResponseMessage response = await client.DeleteAsync("api/SanPham/" + id.ToString());
                return RedirectToAction("Index");
            }
        }
    }
}