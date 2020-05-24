using CourseClient.Entity;
using CourseClient.GlobalVariable;
using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Text.Json;

namespace CourseClient.Server
{
    public static class ServerManager
    {
        public static IURLFormation Formater { get; set; } = new BasicURLPolitic();
        public static int DefaultTimeOut { get; set; } = 10000;

        public static List<T> GetTable<T>() where T : IEntity, new()
        {
            IURLFormation URLFormation = Formater;
            WebRequest request = BuildWebRequest(URLFormation.GetShowAllURL<T>());
            return JsonSerializer.Deserialize<List<T>>(GetStringResponse(request));
        }

        public static string EditEntity<T>(T entity) where T : IEntity
        {
            IURLFormation URLFormation = Formater;
            WebRequest request = BuildWebRequest(URLFormation.GetEditURL(entity));
            AddJsonToRequest(request, entity);
            return GetStringResponse(request);
        }
        public static string addEntity<T>(T entity) where T : IEntity
        {
            IURLFormation URLFormation = Formater;
            WebRequest request = BuildWebRequest(URLFormation.GetAddURL(entity));
            AddJsonToRequest(request, entity);
            return GetStringResponse(request);
        }

        public static string RemoveEnity(IEntity entity)
        {
            IURLFormation URLFormation = Formater;
            WebRequest request = BuildWebRequest(URLFormation.GetRemoveURL(entity));
            return GetStringResponse(request);
        }

        private static void AddJsonToRequest<T>(WebRequest request, T entity) where T : IEntity
        {
            request.ContentType = "application/json";
            using (var streamWriter = new StreamWriter(request.GetRequestStream()))
            {
                string json = JsonSerializer.Serialize(entity);
                streamWriter.Write(json);
            }
        }

        private static string GetStringResponse(WebRequest request)
        {
            Stream objStream;
            objStream = request.GetResponse().GetResponseStream();
            StreamReader objReader = new StreamReader(objStream);
            return objReader.ReadToEnd();
        }

        private static WebRequest BuildWebRequest(string url)
        {
            WebRequest webRequest;
            var authString = Convert.ToBase64String(Encoding.Default.GetBytes(User.Login + ":" + User.Password));
            webRequest = WebRequest.Create(url);
            webRequest.Headers["Authorization"] = "Basic " + authString;
            webRequest.Method = "POST";
            webRequest.Timeout = DefaultTimeOut;
            return webRequest;
        }
    }
}
