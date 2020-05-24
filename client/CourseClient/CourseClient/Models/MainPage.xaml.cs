using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using Xamarin.Forms;
using System.IO;
using CourseClient.Entity;
using System.Text.Json;
using System.Collections;
using CourseClient.GlobalVariable;
using CourseClient.Server;

namespace CourseClient
{
    //[DesignTimeVisible(false)]
    public partial class MainPage : ContentPage
    {
        readonly string MainURL = ConnectConfig.GetUrl();
        WebRequest webRequest;
        public MainPage()
        {
            InitializeComponent();
        }

        private async void Button1_Clicked(object sender, EventArgs e)
        {
            User.Login = login1.Text;
            User.Password = password1.Text;
            var authString = Convert.ToBase64String(Encoding.Default.GetBytes(User.Login + ":" + User.Password));
            webRequest = WebRequest.Create(MainURL);
            webRequest.Timeout = 10000;
            webRequest.Headers["Authorization"] = "Basic " + authString; ;
            webRequest.Method = "POST";
            try
            {
                LoadingIndicator.IsRunning = true;
                await webRequest.GetResponseAsync();
                LoadingIndicator.IsRunning = false;
                await Navigation.PushAsync(new PersonalArea());
            }
            catch (WebException ex)
            {
                LoadingIndicator.IsRunning = false;
                Console.WriteLine(ex.Status);
                string answer;
                switch (ex.Status)
                {
                    case WebExceptionStatus.Timeout:
                        answer = "Сервер временно недоступен, повторите попытку позже";
                        break;
                    case WebExceptionStatus.ProtocolError:
                        answer = "Неправильный логин или пароль";
                        break;
                    case WebExceptionStatus.ConnectFailure:
                        answer = "Ошибка подключения к серверу";
                        break;
                    default:
                        answer = "Ошибка аутентификации";
                        break;
                }
                await DisplayAlert("Ошибка", answer, "OK");
            }
        }
    }
}
