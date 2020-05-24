using CourseClient.Entity;
using CourseClient.Server;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CourseClient.Models.Additor
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class SubjectAdd : ContentPage
    {
        public event UpdateEvent update;
        public SubjectAdd()
        {
            InitializeComponent();
        }

        private void SaveButton_Clicked(object sender, EventArgs e)
        {
            if (!string.IsNullOrEmpty(SubjectName.Text))
            {
                SubjectsEntity subject = new SubjectsEntity();
                subject.name = SubjectName.Text;
                try
                {
                    string answer = ServerManager.addEntity(subject);
                    DisplayAlert("Уведомление", answer, "OK");
                    update?.Invoke();
                    Navigation.PopAsync();
                }
                catch (WebException)
                {
                    DisplayAlert("Ошибка", "Ошибка соединения", "OK");
                    Navigation.PopAsync();
                }
            }
        }
    }
}