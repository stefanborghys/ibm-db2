 �  "      T   �  '   T   )  }   V   �  �   V     �   T     2  T   !  j  T   \  �  V   ]  �  V   �  F  V   @  �  T   {  4  T   �  j  V   �    V   �  I  V   �  �  V     �  T     �  V     b  T   	  �  T   �  �  T   |  �  T   }  L  V   ~  �  T     �  V   �  O	  T   �  �	  T   �  
  V   �  {
  V   �  �
  T   �  +  V   �  �  T   �  �  V   �  �  T   �  e  T   �  �  T   �    T   �  x  T   �  �  V   �  _  V   �  �  V   �  T  T   �  �  T   �  -  T   �  �  T   �    T   �  g  V   �  �  V   �  )  T   �  �  V   �    V   �  �  T   �  �  T   �  c  V   �  �  V   �  7  V   �  |  T   �  �  T   p    V   q  N  V   SQL0290N  不允许访问表空间。  SQL0473N  不允许使用与内置数据类型同名的用户定义的数据类型。 SQL0553N  不能以模式名 "%1S" 来创建对象。 SQL1013N  找不到数据库别名或数据库名称 "%1S"。 SQL1031N  在指示的文件系统中找不到数据库目录。 SQL1032N  未发出启动数据库管理器的命令。 SQL1057W  系统数据库目录为空。 SQL1116N  因为 BACKUP PENDING，所以不能连接或激活数据库 "%1S"。 SQL1117N  因为处于 ROLL-FORWARD PENDING 状态，所以不能连接或激活数据库 "%1S"。 SQL1249N  不支持 DATALINK 数据类型。必须废弃或改变 "%1S" 以避免使用 DATALINK 数据类型。 SQL1344N  在系统目录中找到了孤立行。在尝试对数据库进行升级之前，请与技术服务代表联系。 SQL1403N  提供的用户名和/或密码不正确。 SQL1498W  数据库中有一些表中包含一个或多个 1 类索引。要将它们转换为 2 类索引，请在对数据库进行升级之前运行 CLP 脚本 "%1S"。 SQL1768N  未能启动 HADR。原因码："%1S"。 SQL1769N  不能完成停止 HADR。原因码："%1S"。 SQL1776N  不能对 HADR 备用数据库发出该命令。原因码："%1S"。 ADM4102W  数据库包含一个或多个称为 NULL 的标识。为了避免与 NULL 关键字冲突，您应当使用双引号来限定 SQL 语句中任何称为 NULL 的标识或者为这些标识定界。 ADM4103W  在工作负载 "%2S" 中，连接属性 "%1S" 包含星号（*）。在对数据库进行升级期间，单个星号（*）将被替换为两个星号（**）。原因码："%3S"。 ADM4104E  为 XML Extender 启用了一个或多个数据库。在升级之前，必须从实例和数据库中除去 XML Extender 功能。 ADM4105W  为 DB2 WebSphere MQ 函数启用了数据库。在对数据库进行升级期间，将删除已为 XML Extender 定义的一组函数。 SQL5005C  操作失败，因为数据库管理器未能访问数据库管理器配置文件或数据库配置文件。 DBT5500N  当前用户没有足够的权限来运行 db2ckupgrade 实用程序。 DBT5501N  db2ckupgrade 实用程序未能打开或写入名为 "%1S" 的文件。 DBT5502N  db2ckupgrade 实用程序失败，因为指定参数数目不正确。 DBT5503N  由于指定了以下无效参数，因此 db2ckupgrade 实用程序失败："%1S"。 DBT5504N  db2ckupgrade 实用程序失败，因为既没有指定数据库，也没有指定 -e 参数。 DBT5505N  db2ckupgrade 实用程序失败，因为没有指定任何输出日志文件名称。 DBT5506N  db2ckupgrade 实用程序失败，因为没有为以下参数指定任何值："%1S"。 DBT5507N  db2ckupgrade 实用程序失败，因为多次指定了以下参数："%1S"。 DBT5508I  db2ckupgrade 实用程序已成功完成。可以对数据库进行升级。 DBT5509N  db2ckupgrade 实用程序失败，因为它未能连接至数据库。数据库："%1S"。 DBT5510W  db2ckupgrade 实用程序发现数据库包含以下类型的对象，考虑升级到的 DB2 数据库版本不支持这些对象：XML 全局变量、使用 XML 参数的已编译 SQL 函数或者返回 XML 类型的已编译 SQL 函数。 DBT5511N  db2ckupgrade 实用程序失败，因为参数太长。参数："%1S"。最大长度："%2S"。 DBT5513N  db2ckupgrade 实用程序失败，因为 SYSCATSPACE 表空间需要更多空间以便完成升级。 DBT5514N  db2ckupgrade 实用程序失败，因为数据库处于复原暂挂状态。 DBT5515N  因为数据库处于备份暂挂状态，所以 db2ckupgrade 实用程序失败。 DBT5516N  因为数据库处于前滚暂挂状态，所以 db2ckupgrade 实用程序失败。 DBT5517N  因为数据库处于不一致状态，所以 db2ckupgrade 实用程序失败。 DBT5518W  db2ckupgrade 实用程序已完成，存在一个或多个警告，但仍然可对数据库进行升级。日志文件："%1S"。 DBT5519N  db2ckupgrade 实用程序失败，因为该实用程序未能取消激活数据库。SQLCODE："%1S"。 DBT5520N  db2ckupgrade 实用程序失败，因为在设置 Query Patroller 的旁路标志时发生了错误。SQLCODE："%1S"。 DBT5521N  因为未对正在升级的实例调用 db2ckupgrade 实用程序，所以此实用程序失败。 DBT5522N  db2ckupgrade 实用程序失败，因为数据库使一个或多个表处于装入暂挂状态。 DBT5523N  db2ckupgrade 实用程序失败，因为数据库使一个或多个表处于重新分发暂挂状态。 DBT5524N  db2ckupgrade 实用程序失败，因为不支持从 DB2 V9.5 对启用了 VARCHAR2 支持的数据库进行升级。 DBT5525N  db2ckupgrade 实用程序失败，存在未处理的错误。 DBT5526W  db2ckupgrade 实用程序未检查任何数据库。输出日志文件名为 "%1S"。 DBT5527N  db2ckupgrade 命令失败，因为未能启动数据库管理器。返回码："%1S"。 DBT5528N  db2ckupgrade 实用程序失败，因为已暂挂或者正在暂挂对数据库的 I/O 写操作。 DBT5529N  db2ckupgrade 实用程序未成功完成。无法对数据库进行升级。输出日志文件名为 "%1S"。 DBT5530N  db2ckupgrade 命令失败，因为表空间处于不正常状态。表空间："%1S"。状态："%2S"。成员："%3S"。 DBT5531W  由于 db2ckupgrade 实用程序找不到任何要处理的本地数据库，因此失败。 DBT5532N  因为有一个或多个 MQT 依赖于系统视图，所以 db2ckupgrade 实用程序失败。 DBT5533N  因为 db2ckupgrade 实用程序未能激活数据库，所以该实用程序失败。SQLCODE："%1S"。 DBT5536W  db2ckupgrade 实用程序找不到任何与数据库 "%1S" 相关联的审计策略。 DBT5537I  db2ckupgrade 实用程序已完成处理数据库 "%1S"。 DBT5538N  db2ckupgrade 实用程序找到意外的安装点配置。 DBT5539N  db2ckupgrade 实用程序在数据库控制文件中找到不一致情况。 正在运行的 DB2CKUPGRADE 的版本：版本 "%1S" 数据库："%1S" 