using CourseClient.Entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CourseClient.Models;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using System.Net;
using System.IO;
using System.Text.Json;
using CourseClient.GlobalVariable;

namespace CourseClient
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class PersonalArea : ContentPage
    {
        public PersonalArea()
        {
            InitializeComponent();
            welcomeLabel.Text = "Добро пожаловать: " + User.Login;
        }

        private void ShowAllTeacherButton_Clicked(object sender, EventArgs e)
        {
            Navigation.PushAsync(new PeopleViewer("P"));  
        }

        private void ShowAllGroupsButton_Clicked(object sender, EventArgs e)
        {
             Navigation.PushAsync(new GroupViewer());
        }

        private void ShowAllMarks_Clicked(object sender, EventArgs e)
        {
            Navigation.PushAsync(new MarkViewer());
        }

        private void ShowAllStudentButton_Clicked(object sender, EventArgs e)
        {
            Navigation.PushAsync(new PeopleViewer("S"));
        }
    }
}