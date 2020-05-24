using CourseClient.Entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using CourseClient.Server;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using CourseClient.Models.Additor;

namespace CourseClient.Models
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class GroupViewer : ContentPage
    {
        public List<GroupsEntity> GroupsList { get; set; }
        public GroupViewer()
        {
            InitializeComponent();
            UpdateData();
            this.BindingContext = this;
        }
        private void UpdateData()
        {
            try
            {
                GroupsList = ServerManager.GetTable<GroupsEntity>().Where(n => n.name != "_").ToList();
                MyListView.ItemsSource = null;
                MyListView.ItemsSource = GroupsList;
            }
            catch (WebException)
            {
                DisplayAlert("Ошибка", "Ошибка соединения", "OK");
                Navigation.PopAsync();
            }
        }

        private void ToolbarItem_Clicked(object sender, EventArgs e)
        { 
            GroupAdd panel = new GroupAdd();
            panel.update += UpdateData;
            Navigation.PushAsync(panel);
        }
    }
}