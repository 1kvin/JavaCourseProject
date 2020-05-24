using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using CourseClient.Entity;
using CourseClient.Models.Editor;
using System.Net;
using System.Threading;
using CourseClient.Server;
using CourseClient.Models.Additor;

namespace CourseClient.Models
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class PeopleViewer : ContentPage
    {
        public List<PeopleEntity> PeoplesList { get; set; }
        private readonly string type;
        public PeopleViewer(string type)
        {
            InitializeComponent();
            this.type = type;
            MyListView.BeginRefresh();
            UpdateData();
            MyListView.EndRefresh();
            this.BindingContext = this;
        }

        private void UpdateData()
        {
            try
            {
                PeoplesList = ServerManager.GetTable<PeopleEntity>().Where(a => a.type == type).ToList();
                MyListView.ItemsSource = null;
                MyListView.ItemsSource = PeoplesList;
            }
            catch (WebException)
            {
                DisplayAlert("Ошибка", "Ошибка соединения", "OK");
                Navigation.PopAsync();
            }
        }

        private async void MyListView_ItemTappedAsync(object sender, SelectedItemChangedEventArgs e)
        {
            var selectedItem = e.SelectedItem as PeopleEntity;
            if (selectedItem == null)
                return;


            bool alertResult = await DisplayAlert("Уведомление", "Что вы хотите сделать?", "Редактировать", "Отмена");
            if(alertResult)
            {
                PeopleEditor peopleEditor = new PeopleEditor(selectedItem);
                peopleEditor.update += UpdateData;
                await Navigation.PushAsync(peopleEditor);
            }
            //Deselect Item
            ((ListView)sender).SelectedItem = null;
        }

        private void ToolbarItem_Clicked(object sender, EventArgs e)
        {
            PeopleAdd panel = new PeopleAdd(type);
            panel.update += UpdateData;
            Navigation.PushAsync(panel);
        }
    }
}