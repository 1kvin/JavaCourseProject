using CourseClient.Entity;
using System;
using System.Collections.Generic;
using System.Text;

namespace CourseClient.Server
{
    public interface IURLFormation
    {
        string GetEditURL(IEntity entity);
        string GetRemoveURL(IEntity entity);
        string GetAddURL(IEntity entity);
        string GetShowAllURL<T>() where T : IEntity, new();
    }
}
