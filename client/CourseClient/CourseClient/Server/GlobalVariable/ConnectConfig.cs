using System;
using System.Collections.Generic;
using System.Text;

namespace CourseClient.GlobalVariable
{
    static class ConnectConfig
    {
        //static readonly string IP = "192.168.0.103";
        static readonly string IP = "192.168.0.106";
        static readonly string PORT = "8080";
        static readonly string PROTOCOL = "http";
        public static string GetUrl()
        {
            return PROTOCOL + "://" + IP + ":" + PORT + "/";
        }
    }
}
