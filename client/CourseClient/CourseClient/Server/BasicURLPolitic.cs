using CourseClient.Entity;
using CourseClient.GlobalVariable;
using System;
using System.Collections.Generic;
using System.Text;

namespace CourseClient.Server
{
    class BasicURLPolitic : IURLFormation
    {
        public string GetAddURL(IEntity entity)
        {
            return ConnectConfig.GetUrl() + "add" + entity.GetViewInTable().Substring(0, 1).ToUpper() + entity.GetViewInTable().Substring(1);
        }

        public string GetEditURL(IEntity entity)
        {
            return ConnectConfig.GetUrl() + "edit" + entity.GetViewInTable().Substring(0, 1).ToUpper() + entity.GetViewInTable().Substring(1);
        }

        public string GetRemoveURL(IEntity entity)
        {
            return ConnectConfig.GetUrl() + "remove?table=" + entity.GetViewInTable() + "&id="+ entity.id;
        }

        public string GetShowAllURL<T>() where T : IEntity, new()
        {
            var entity = new T();
            return ConnectConfig.GetUrl() + "show?table="+ entity.GetViewInTable();
        }
    }
}
